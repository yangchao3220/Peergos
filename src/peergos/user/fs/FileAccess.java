package peergos.user.fs;

import org.bouncycastle.util.Arrays;
import peergos.crypto.SymmetricLink;
import peergos.crypto.symmetric.SymmetricKey;
import peergos.util.ByteArrayWrapper;
import peergos.util.Serialize;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class FileAccess {
    public static class EncryptedFileProperties{
        //todo : wrap byte array
    }
    public static class  FileRetriever{
        //todo
    }
    public static class ReadableFilePointer {

    }
    public static class SymmetricLocationLink {
        public final ByteArrayWrapper link;
        public final Location location;

        public SymmetricLocationLink(ByteArrayWrapper link, Location location) {
            this.link = link;
            this.location = location;
        }


        public static SymmetricLocationLink deserialize(DataInputStream din) throws IOException {
            byte[] link = Serialize.deserializeByteArray(din, 0x1000);
            Location location = Location.deserialize(din);
            return new SymmetricLocationLink(
                    new ByteArrayWrapper(link), location);
        }

        public void serialize(DataOutputStream dout) throws IOException {
            dout.write(link.data);
            dout.write(location.serialize());
        }

        public static SymmetricLocationLink create(SymmetricKey fromKey, SymmetricKey toKey, Location location) {

            ByteArrayWrapper nonce = new ByteArrayWrapper(
                    fromKey.createNonce());

            byte[] bytes = fromKey.encrypt(toKey.toByteArray(), nonce.data);
            byte[] link = Arrays.concatenate(nonce.data, bytes);

            return new SymmetricLocationLink(
                    new ByteArrayWrapper(link),
                    location);
        }
    }


//    private final SymmetricLink parentToMeta;
//    private final EncryptedFileProperties fileProperties;
//    private final FileRetriever retriever;
//    private final SymmetricLocationLink parentLink;

}
