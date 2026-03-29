package com.customnpcs.craftingview;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.minecraftforge.common.Configuration;

public class Config {

    private static final List<CategoryDefinition> categoriesMutable = new ArrayList();
    public static final List<CategoryDefinition> categories = Collections.unmodifiableList(categoriesMutable);

    public static void load(File configFile) {
        Configuration cfg = new Configuration(configFile);
        try {
            cfg.load();

            int count = 0;
            while (cfg.hasKey("categories", "category_" + count)) {
                count++;
            }

            if (count == 0) {
                cfg.get("categories", "category_0", "示例分类|1,2,3|").comment =
                    "格式: 分类名|recipeId1,recipeId2,...|recipeName1,recipeName2,...  留空表示不过滤";
                cfg.save();
                return;
            }

            categoriesMutable.clear();
            for (int i = 0; i < count; i++) {
                String raw = cfg.get("categories", "category_" + i, "").getString();
                CategoryDefinition def = parse(raw);
                if (def != null) categoriesMutable.add(def);
            }
        } finally {
            if (cfg.hasChanged()) cfg.save();
        }
    }

    private static CategoryDefinition parse(String raw) {
        if (raw == null || raw.trim().isEmpty()) return null;
        String[] parts = raw.split("\\|", -1);
        String name = parts.length > 0 ? parts[0].trim() : "";
        if (name.isEmpty()) return null;

        List ids = new ArrayList();
        if (parts.length > 1 && !parts[1].trim().isEmpty()) {
            for (String s : parts[1].split(",")) {
                try { ids.add(Integer.valueOf(s.trim())); } catch (NumberFormatException ignored) {}
            }
        }

        List names = new ArrayList();
        if (parts.length > 2 && !parts[2].trim().isEmpty()) {
            for (String s : parts[2].split(",")) {
                String n = s.trim();
                if (!n.isEmpty()) names.add(n.toLowerCase());
            }
        }

        return new CategoryDefinition(name, ids, names);
    }

    public static class CategoryDefinition {
        public final String name;
        public final List recipeIds;
        public final List recipeNames;

        public CategoryDefinition(String name, List recipeIds, List recipeNames) {
            this.name = name;
            this.recipeIds = recipeIds;
            this.recipeNames = recipeNames;
        }
    }
}
