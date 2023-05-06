package fr.stevie.utils;

import fr.stevie.XMaterial;

public class MaterialGUI {

    public static String getMaterial(String material){
        return XMaterial.matchXMaterial(material).get().parseMaterial().name();
    }

}