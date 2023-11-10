package appu26j.utils;

import java.io.*;
import java.nio.file.Files;
import java.util.zip.*;

public class FileUtil
{
    public static boolean unzip(File input, File output)
    {
        try
        {
            if (!output.exists())
            {
                output.mkdirs();
            }

            ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(input.toPath()));
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null)
            {
                File file = new File(output, zipEntry.getName());

                if (!zipEntry.isDirectory())
                {
                    extractFile(zipInputStream, file);
                }

                else
                {
                    file.mkdirs();
                }

                zipInputStream.closeEntry();
                zipEntry = zipInputStream.getNextEntry();
            }

            zipInputStream.close();
            return true;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private static void extractFile(ZipInputStream zipInputStream, File file) throws Exception
    {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] bytes = new byte[4096];
        int read = 0;

        while ((read = zipInputStream.read(bytes)) != -1)
        {
            bufferedOutputStream.write(bytes, 0, read);
        }

        bufferedOutputStream.close();
    }
}
