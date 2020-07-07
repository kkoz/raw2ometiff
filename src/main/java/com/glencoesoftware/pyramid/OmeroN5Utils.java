package com.glencoesoftware.pyramid;

import java.io.IOException;

import org.janelia.saalfeldlab.n5.N5Reader;

import loci.common.Region;
import net.imglib2.img.Img;
import net.imglib2.type.Type;

public abstract class OmeroN5Utils {
    public abstract <T extends Type<T>> Img<T> getTileImage(
            N5Reader n5Reader, Integer tileSizeX, Integer tileSizeY,
            String datasetPath, int resolution,
            int[] pos, int x, int y, Region region) throws IOException;

}
