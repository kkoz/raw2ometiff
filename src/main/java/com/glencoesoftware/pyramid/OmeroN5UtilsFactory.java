package com.glencoesoftware.pyramid;

import org.janelia.saalfeldlab.n5.DataType;

public class OmeroN5UtilsFactory {
    public static OmeroN5Utils getUtils(DataType dataType) {
        switch(dataType) {
            case UINT8:
                System.out.println("UINT8");
                return new OmeroN5UnsignedByteUtils();
            case UINT16:
                System.out.println("UINT16");
                break;
            case UINT32:
                System.out.println("UINT32");
                break;
            case UINT64:
                System.out.println("UINT64");
                break;
            case INT8:
                System.out.println("INT8");
                break;
            case INT16:
                System.out.println("INT16");
                break;
            case INT32:
                System.out.println("INT32");
                break;
            case INT64:
                System.out.println("INT64");
                break;
            case FLOAT32:
                System.out.println("FLOAT32");
                break;
            case FLOAT64:
                System.out.println("FLOAT64");
                break;
        }
        return null;
    }

}
