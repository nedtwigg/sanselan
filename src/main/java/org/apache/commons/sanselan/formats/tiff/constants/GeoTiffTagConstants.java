package org.apache.commons.sanselan.formats.tiff.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.sanselan.formats.tiff.taginfos.TagInfo;
import org.apache.commons.sanselan.formats.tiff.taginfos.TagInfoAscii;
import org.apache.commons.sanselan.formats.tiff.taginfos.TagInfoDouble;
import org.apache.commons.sanselan.formats.tiff.taginfos.TagInfoShort;

/**
 * Geo Tiff.
 * <BR>
 * http://www.remotesensing.org/geotiff/spec/contents.html
 */
public interface GeoTiffTagConstants {
    public static final TagInfoShort EXIF_TAG_GEO_TIFF_DIRECTORY = new TagInfoShort(
            "Geo Tiff Directory", 0x87af, -1,
            TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
    
    public static final TagInfoDouble EXIF_TAG_GEO_TIFF_DOUBLE_PARAMS = new TagInfoDouble(
            "Geo Tiff Double Params", 0x87b0, -1,
            TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
    
    public static final TagInfoAscii EXIF_TAG_GEO_TIFF_ASCII_PARAMS = new TagInfoAscii(
            "Geo Tiff Ascii Params", 0x87b1, -1,
            TiffDirectoryType.EXIF_DIRECTORY_UNKNOWN);
    
    public static final List<TagInfo> ALL_GEO_TIFF_TAGS =
            Collections.unmodifiableList(Arrays.asList(
                    EXIF_TAG_GEO_TIFF_DIRECTORY,
                    EXIF_TAG_GEO_TIFF_DOUBLE_PARAMS,
                    EXIF_TAG_GEO_TIFF_ASCII_PARAMS));

}