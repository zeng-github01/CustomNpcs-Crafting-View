# Quality Guidelines

> Code quality standards for backend development in this legacy Forge mod project.

---

## Overview

This project is a Minecraft Forge 1.6.4 / Java 7 mod. Quality work prioritizes:

- keeping optional mod integrations load-safe when the optional mod is absent;
- avoiding client-only class references from common/server code;
- preserving existing CustomNPCs Carpentry/Anvil behavior while adding compatibility paths;
- validating with Java 8 for Java 7 source/target compatibility.

---

## Scenario: Optional Forge Mod GUI Compatibility

### 1. Scope / Trigger

Use this contract when adding support for a GUI/container from an optional mod, or when adding a client/server packet that crosses the GUI boundary.

This is mandatory for compatibility features because direct references to optional mod classes can crash mod loading when the optional dependency is not installed.

### 2. Signatures

- GUI detection: compare class names as strings, e.g. `gui.getClass().getName().equals("twilightforest.client.GuiTFGoblinCrafting")`.
- Container detection: compare class names as strings, e.g. `container.getClass().getName().equals("twilightforest.ContainerTFUncrafting")`.
- Optional container fields/methods: access by reflection only, e.g. public field `assemblyMatrix` or methods `getUncraftingCost`, `getRecraftingCost`.
- Network packets on `Packet250CustomPayload` must include a leading packet type byte before payload fields.

### 3. Contracts

For the Twilight Forest uncrafting integration, the packet contracts are:

- `PACKET_REQUEST_GLOBAL_RECIPES`
  - Direction: client -> server.
  - Payload: `{ byte type }`.
  - Server response: `PACKET_GLOBAL_RECIPES`.
- `PACKET_GLOBAL_RECIPES`
  - Direction: server -> client.
  - Payload: `{ byte type, NBTTagCompound compound }`.
  - Compound key: `recipes` as `NBTTagList` of `RecipeCarpentry.writeNBT()` entries.
  - Entries must come from `RecipeController.instance.globalRecipes` and be filtered to `recipeWidth <= 3 && recipeHeight <= 3`.
- `PACKET_FILL_TWILIGHT_GRID`
  - Direction: client -> server.
  - Payload: `{ byte type, int recipeId }`.
  - Server must resolve `recipeId` from `RecipeController.instance.globalRecipes` only, not `RecipeController#getRecipe`, so anvil/carpentry recipes cannot be filled into a 3x3 target.

### 4. Validation & Error Matrix

- Optional GUI class absent -> no code path should load that class directly; feature is skipped.
- Open container class name mismatch -> ignore fill packet.
- Missing/reflection field unavailable -> ignore packet and log only if useful; do not crash the server.
- Recipe id missing from `globalRecipes` -> ignore packet.
- Recipe width/height greater than 3 -> ignore packet.
- Existing target grid items -> return to player inventory first; if inventory is full, drop them.
- Player lacks some ingredients -> fill only ingredients found; do not create items.

### 5. Good/Base/Bad Cases

- Good: Twilight Forest installed, player opens uncrafting table, client requests global recipes once, panel shows only CustomNPCs 3x3 global recipes, `+` fills `assemblyMatrix`.
- Base: Twilight Forest absent, existing CustomNPCs Carpentry panel still opens and fills as before.
- Bad: Server handles a Twilight fill packet while another container is open; packet is ignored.
- Bad: A recipe id exists in `anvilRecipes` only; Twilight fill rejects it.

### 6. Tests Required

For this legacy Forge codebase, automated tests may be impractical. Required checks are:

- `git diff --check` for whitespace/conflict markers.
- Gradle build with local Java 8, e.g. `./gradlew build --offline -Dorg.gradle.java.home="C:/Program Files/Zulu/zulu-8"`.
- Static search that optional mod references are string/reflection only, e.g. `rg "twilightforest\." src/main/java` should not show imports or typed references.
- Manual in-game smoke checks when possible:
  - no Twilight Forest installed -> mod starts and existing Carpentry behavior works;
  - Twilight Forest installed -> uncrafting table panel appears and uses 3x3 global recipes.

### 7. Wrong vs Correct

#### Wrong

```java
import twilightforest.ContainerTFUncrafting;

if (player.openContainer instanceof ContainerTFUncrafting) {
    ContainerTFUncrafting container = (ContainerTFUncrafting) player.openContainer;
    // ...
}
```

This creates a hard dependency on Twilight Forest and can fail when the mod is absent.

#### Correct

```java
if (player.openContainer != null
        && "twilightforest.ContainerTFUncrafting".equals(player.openContainer.getClass().getName())) {
    Field field = player.openContainer.getClass().getField("assemblyMatrix");
    InventoryCrafting assembly = (InventoryCrafting) field.get(player.openContainer);
    // ...
}
```

This keeps Twilight Forest optional and confines compatibility behavior to the runtime path where the container actually exists.

---

## Forbidden Patterns

- Do not import optional mod classes from common/server code.
- Do not route 3x3 optional GUI fill packets through a recipe lookup that can return CustomNPCs 4x4 anvil/carpentry recipes.
- Do not assume CustomNPCs global recipes have already been synced to the client; direct GUI integrations should request the needed data.

---

## Required Patterns

- Keep existing CustomNPCs Carpentry/Anvil paths unchanged unless the task explicitly asks for a behavior change.
- Keep CustomNPCs Carpentry/Anvil and vanilla-workbench/Twilight recipe concerns separated:
  - shared UI renderer code may be reused, but the panel model must carry an explicit source/context rather than a boolean whose meaning can leak across callers;
  - CustomNPCs Carpentry/Anvil panels must read and fill from `RecipeController.instance.anvilRecipes` only;
  - Twilight/vanilla-workbench panels must read and fill from `RecipeController.instance.globalRecipes` only;
  - config categories for these contexts must be separate so a workbench compatibility category cannot hide Carpentry/Anvil recipes.
- For optional integrations, use class-name checks plus reflection for optional fields/methods.
- For client-only behavior, route through the sided proxy so common/server code does not reference client classes.

---

## Testing Requirements

- Run `git diff --check` before handoff.
- Run the Gradle build with Java 8 for this Java 7 target project when practical.
- Document any environment-specific validation caveats in the handoff.

---

## Code Review Checklist

- [ ] Optional dependency classes are not imported directly.
- [ ] Network packet directions and payload fields are explicit.
- [ ] Server packet handlers validate the currently open container before mutating inventories.
- [ ] Recipe sources match the target grid type (`globalRecipes` for vanilla 3x3; `anvilRecipes` for CustomNPCs Carpentry/Anvil), including both display and fill packet handling.
- [ ] Config categories are scoped to the same recipe source as the panel using them.
- [ ] Existing inventory contents are returned/dropped before being overwritten.
- [ ] Build/check commands and Java version are recorded.
