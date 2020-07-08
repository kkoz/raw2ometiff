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
                return new OmeroN5UnsignedShortUtils();
            case UINT32:
                System.out.println("UINT32");
                return new OmeroN5UnsignedIntUtils();
            case UINT64:
                System.out.println("UINT64");
                return new OmeroN5UnsignedLongUtils();
            case INT8:
                System.out.println("INT8");
                return new OmeroN5ByteUtils();
            case INT16:
                System.out.println("INT16");
                return new OmeroN5ShortUtils();
            case INT32:
                System.out.println("INT32");
                return new OmeroN5IntUtils();
            case INT64:
                System.out.println("INT64");
                return new OmeroN5LongUtils();
            case FLOAT32:
                System.out.println("FLOAT32");
                return new OmeroN5FloatUtils();
            case FLOAT64:
                System.out.println("FLOAT64");
                return new OmeroN5DoubleUtils();
        }
        return null;
    }

}
