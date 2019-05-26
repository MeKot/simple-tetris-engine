package te.tetris

class TestingUtils {

    static final deleteQuietly(File f) {
        try {
            f.delete()
        } catch (all) {
            // Do nothing
        }
    }

}
