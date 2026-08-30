package com.sh1nylabs.bonesupdate.registerer;

import net.minecraft.resources.Identifier;

public class BUModIdentifier {
    public static final String MODID = "bonesupdate";
    public static Identifier fromModNamespace(String path)
    {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
