package com.glencoesoftware.pyramid;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.janelia.saalfeldlab.n5.DataBlock;
import org.janelia.saalfeldlab.n5.N5Reader;
import org.janelia.saalfeldlab.n5.imglib2.N5Utils;

import loci.common.Region;
import loci.formats.FormatException;
import loci.formats.FormatTools;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImg;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.basictypeaccess.array.ByteArray;
import net.imglib2.type.Type;
import net.imglib2.type.numeric.integer.GenericByteType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.real.DoubleType;

public class OmeroN5UnsignedByteUtils extends OmeroN5Utils {

    @Override
    public <T extends Type<T>> Img<T> getTileImage(
            N5Reader n5Reader, Integer tileSizeX, Integer tileSizeY,
            String datasetPath, int resolution,
            int[] pos, int x, int y, Region region) throws IOException {

        RandomAccessibleInterval<UnsignedByteType> source = N5Utils.open(n5Reader, datasetPath);
        RandomAccess<UnsignedByteType> r = source.randomAccess();

        long[] gridPosition = new long[] {x, y, pos[0], pos[1], pos[2]};

        r.setPosition(gridPosition);

        //Create an image and copy the values from n5
        //TODO: Need to check for region/tile outside image bounds?
        ArrayImg<UnsignedByteType, ByteArray> img = ArrayImgs.unsignedBytes(region.width, region.height);
        RandomAccess<UnsignedByteType> dest = img.randomAccess();

        for(int i = 0; i < region.width; i++) {
            for(int j = 0; j < region.height; j++) {
                UnsignedByteType sampler = r.get();
                dest.get().set(sampler.getByte());
            }
        }
        return (Img<T>) img;
      }
}
