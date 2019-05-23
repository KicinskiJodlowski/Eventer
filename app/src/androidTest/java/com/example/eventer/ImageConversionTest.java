package com.example.eventer;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Base64;

import com.example.eventer.fragment.AddEventFragment;
import com.example.eventer.fragment.EventDetailsFragment;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ImageConversionTest {
    @Test
    public void convertToBase64Test() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        Uri path = Uri.parse("android.resource://com.example.eventer/raw/logotest");
        String test = "/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAKBueIx4ZKCMgoy0qqC+8P//8Nzc8P//////////////\n////////////////////////////////////////////2wBDAaq0tPDS8P//////////////////\n////////////////////////////////////////////////////////////wAARCAEAAQADASIA\nAhEBAxEB/8QAGAABAQEBAQAAAAAAAAAAAAAAAAECAwT/xAAmEAEBAAICAgICAgIDAAAAAAAAAQIR\nAzESIUFRMmETcSJSQoGR/8QAFgEBAQEAAAAAAAAAAAAAAAAAAAEC/8QAFhEBAQEAAAAAAAAAAAAA\nAAAAAAER/9oADAMBAAIRAxEAPwDiAAAAAAAAAAElvTc47e/QMDrOPGftqSTqRNHDV+l8b9V2DRx8\nb9VNX6dw0cB3sl7kZvHjf0aOQ1eOzr2z0oAAAAAAAAAAAAAAAAAuONyBO28eP/b/AMbxxmPTSaJJ\nJ1BURAAAAAAAAAsl7gA55cf+rDumWMyXVcRcsbjfaKAAAAAAAAAAALhj5X9AuGHl/TrJqahPUVlA\nABFAQAAAAAAAAAAACyWarjlj439OxZuaoOAuWPjUaUAAAAAAAAk3dR3k1NRnjx1N/baVABAAABAA\nABKi4rQysMFARAAAAAAEynlNONmrqu7HJj62sHMBVAAAAFxnllpHTinrf2DagygAAAAgAAmwKhbs\naUWIAqpsZRQAAAAAAAccpq6R05Z1XNpQAAAB3k1JHHCbyjslBUERRAAAAAEvaLe0aUFgmiClNEIE\nUVUGUUAAAAAEym8bHF3ccprKrFQBQABrj/J1c+LuuiVABAAAAAABL2i3tGlUIMoAVRCdhO1VVBlA\nAAAAABy5PydXPl/JYMAKoADfF3XVy4u66s1AAAAAAAAEs+mXXGfKZ4b9ztYrmbBQ2AAsjeOOvd7M\np8pRkBEG5jNMOixU8YmUkjbOfwDICIOXL+UdXHl/IgyA0oADXHf83Z55dWV6EoAIgAAAAT3RrGfI\nNANK554zthvK7rOk0RvDH5+WZGsbqmjZfYKMI1lPlGUJN10ZxnrbSxRjLttzvulABEHDO7zru899\n1YoAoAAO2F3jHFvivvSUdQEQAAABZN1tJNRVijOV1Fc7d3ZQARAAG8buKxLqtrFGZj7aVQBATK+m\nS3dGagADPJdY/wBuLfJd5a+mFigCgAAS6uwB3l3Nq5ceWrq9V1ZQAAXGe0bk1CCgNKzlLZ6YdU1t\nMHMb8YeH7MRgb8P2eMTBlrGXXtVXFAFBjK7avuMJQARBMr4zauPJlu6+IQZAaUAAAAAAdePLc1e3\nIl1dwHoGcMvKftqTdZRrGfLQNKJuJldRzQdhzmVjUzi6NCeU+zcBRNw8oCjPkltqaNbGGpdw0VnK\nfLQowDOefjP2yicmWpqduR2NKAAAAAAAAAAS6u49PHvxlvdcOLDyy/UeoAGc7qaBjK7qAyigAAAA\nAoACy6qANiSq0rHJuY7jz27et5uTHxy18fAMgAAAAAAAAAA6cOG75XqA68ePhjr5+WwBLdTdebLO\n3K1058/+M/7cQbnJ9tyy9OImDuOUzsWcn3DEdBj+SL54/aDSsfyY/Z/JAbHO8v1GbnlTB1tk7rGX\nJ9OYuK1jnZlu16XkduHLc8b8KOrHJj5Y/uNgPIN8uPjludVgAAAAAAAAB6eKa448z1YfhP6BpnLL\nxxtW2Yzdrz8nJ5310DFu7ugAAAAAAAAAAAAAGN8bLAB6sb5SWK8/HyePq9O8ss3AZ5ZvjrzvTyfh\nf6eYAAAAAAAAB3vLjjjNe64ALllcru1AAAAAAAAAAAAAAAAAAXHK430gDv8AyY5YXfq6cAAAAAB/\n/9k=\n";

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(appContext.getContentResolver(), path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        AddEventFragment fragment = new AddEventFragment();
        String base64 = fragment.convertToBase64(bitmap);

        assertEquals(test,base64);
    }
}
