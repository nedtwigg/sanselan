/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sanselan.formats.tiff.write;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.common.BinaryOutputStream;
import org.apache.sanselan.util.Debug;
import org.apache.sanselan.util.DebugOutputStream;

public class TiffImageWriterLossless extends TiffImageWriterBase
{
	private final byte exifBytes[];

	public TiffImageWriterLossless(byte exifBytes[])
	{
		this.exifBytes = exifBytes;
	}

	public TiffImageWriterLossless(int byteOrder, byte exifBytes[])
	{
		super(byteOrder);
		this.exifBytes = exifBytes;
	}

	public void write(OutputStream os, TiffOutputSet outputSet)
			throws IOException, ImageWriteException
	{
		List directories = outputSet.getDirectories();

		TiffOutputSummary outputSummary = validateDirectories(directories);

		List outputItems = outputSet.getOutputItems(outputSummary);

		updateOffsetsStep(outputItems);

		outputSummary.updateOffsets(byteOrder);

		BinaryOutputStream bos = new BinaryOutputStream(os, byteOrder);

		writeStep(bos, outputItems);

	}

	private void updateOffsetsStep(List outputItems) throws IOException,
			ImageWriteException
	{
		//		int offset = TIFF_HEADER_SIZE;
		int offset = exifBytes.length;

		for (int i = 0; i < outputItems.size(); i++)
		{
			TiffOutputItem outputItem = (TiffOutputItem) outputItems.get(i);

			outputItem.setOffset(offset);
			int itemLength = outputItem.getItemLength();
			offset += itemLength;

			int remainder = imageDataPaddingLength(itemLength);
			offset += remainder;
		}
	}

	private void writeStep(BinaryOutputStream bos, List outputItems)
			throws IOException, ImageWriteException
	{
		writeImageFileHeader(bos);

		bos.write(exifBytes, TIFF_HEADER_SIZE, exifBytes.length
				- TIFF_HEADER_SIZE);

		//		int offset = TIFF_HEADER_SIZE;
		int offset = exifBytes.length;
		for (int i = 0; i < outputItems.size(); i++)
		{
			TiffOutputItem outputItem = (TiffOutputItem) outputItems.get(i);

			outputItem.writeItem(bos);

			int length = outputItem.getItemLength();
			offset += length;
			int remainder = imageDataPaddingLength(length);
			offset += remainder;
			for (int j = 0; j < remainder; j++)
				bos.write(0);
		}

	}

	private void writeImageFileHeader(BinaryOutputStream bos)
			throws IOException, ImageWriteException
	{
		bos.write(byteOrder);
		bos.write(byteOrder);

		bos.write2Bytes(42); // tiffVersion

		//		int foffsetToFirstIFD = TIFF_HEADER_SIZE;
		int offsetToFirstIFD = exifBytes.length;

		bos.write4Bytes(offsetToFirstIFD);
	}

}