package com.glencoesoftware.pyramid;

import java.io.IOException;

import org.janelia.saalfeldlab.n5.DataType;
import org.janelia.saalfeldlab.n5.N5Reader;

import loci.common.Region;
import net.imglib2.Cursor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.img.Img;
import net.imglib2.img.array.ArrayImgs;
import net.imglib2.img.basictypeaccess.ByteAccess;
import net.imglib2.type.Type;
import net.imglib2.type.numeric.integer.ByteType;
import net.imglib2.type.numeric.integer.IntType;
import net.imglib2.type.numeric.integer.LongType;
import net.imglib2.type.numeric.integer.ShortType;
import net.imglib2.type.numeric.integer.UnsignedByteType;
import net.imglib2.type.numeric.integer.UnsignedIntType;
import net.imglib2.type.numeric.integer.UnsignedLongType;
import net.imglib2.type.numeric.integer.UnsignedShortType;
import net.imglib2.type.numeric.real.DoubleType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.IntervalView;
import net.imglib2.view.Views;

public class OmeroN5Utils {
    public <T extends Type<T>> Img<T> getTileImage(
            N5Reader n5Reader, Integer tileSizeX, Integer tileSizeY,
            String datasetPath, int resolution,
            int[] pos, int x, int y, Region region) throws IOException{
        return null;
    }

    public Img<?> getTileImgCopy(
        Img<?> source, DataType dataType,
        Integer tileSizeX, Integer tileSizeY,
        int[] pos, int x, int y, Region region) throws IOException {
        long[] dimensions = new long[source.numDimensions()];
        int[] intBlockDimensions = new int[source.numDimensions()];
        source.dimensions(dimensions);
        for(int i = 0; i < dimensions.length; i++) {
            intBlockDimensions[i] = (int) dimensions[i];
        }
        long[] offset = new long[] {x, y, pos[0], pos[1], pos[2]};
        long[] dimension = new long[] {tileSizeX, tileSizeY, 1, 1, 1};

        switch(dataType) {
            case UINT8:
                Img<UnsignedByteType> ubyteSource = (Img<UnsignedByteType>) source;
                return getTileFromImage(ubyteSource, offset, dimension);
//                return getTileFromImage((RandomAccessibleInterval<UnsignedByteType>) source,
//                        ArrayImgs.unsignedBytes(dimensions), offset, dimension);
            case INT8:
                return getTileFromImage((RandomAccessibleInterval<ByteType>) source,
                        ArrayImgs.bytes(dimensions), offset, dimension);
            case UINT16:
                return getTileFromImage((RandomAccessibleInterval<UnsignedShortType>) source,
                        ArrayImgs.unsignedShorts(dimensions), offset, dimension);
            case INT16:
                return getTileFromImage((RandomAccessibleInterval<ShortType>) source,
                        ArrayImgs.shorts(dimensions), offset, dimension);
            case UINT32:
                return getTileFromImage((RandomAccessibleInterval<UnsignedIntType>) source,
                        ArrayImgs.unsignedInts(dimensions), offset, dimension);
            case INT32:
                return getTileFromImage((RandomAccessibleInterval<IntType>) source,
                        ArrayImgs.ints(dimensions), offset, dimension);
            case UINT64:
                return getTileFromImage((RandomAccessibleInterval<UnsignedLongType>) source,
                        ArrayImgs.unsignedLongs(dimensions), offset, dimension);
            case INT64:
                return getTileFromImage((RandomAccessibleInterval<LongType>) source,
                        ArrayImgs.longs(dimensions), offset, dimension);
            case FLOAT32:
                return getTileFromImage((RandomAccessibleInterval<FloatType>) source,
                        ArrayImgs.floats(dimensions), offset, dimension);
            case FLOAT64:
                return getTileFromImage((RandomAccessibleInterval<DoubleType>) source,
                        ArrayImgs.doubles(dimensions), offset, dimension);
        }
        return null;
    }

    public <T extends Type<T>> Img<T> getTileFromImage(
            Img<T> source, long[] offset, long dimension[]) throws IOException {
        IntervalView<T> intervalView = Views.offsetInterval(source, offset, dimension);
        long[] targetDimensions = new long[intervalView.numDimensions()];
        intervalView.dimensions(targetDimensions);
        Img<T> target = source.factory().create(targetDimensions);
        return deepCopy(intervalView, target);
    }

    public <T extends Type<T>> Img<T> getTileFromImage(
            RandomAccessibleInterval<T> source, Img<T> target, long[] offset, long dimension[]) throws IOException {
        IntervalView<T> intervalView = Views.offsetInterval(source, offset, dimension);
        return deepCopy(intervalView, target);
    }

    public <T extends Type<T>> Img<T> deepCopy(IntervalView<T> source, Img<T> target) {
        for(Cursor<T> s = Views.flatIterable(source).cursor(), t = Views.flatIterable(target).cursor();
                t.hasNext();) {
            t.next().set(s.next());
        }
        return target;
    }

}
