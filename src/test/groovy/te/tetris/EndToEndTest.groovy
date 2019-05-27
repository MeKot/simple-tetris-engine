package te.tetris

import spock.lang.Specification

class EndToEndTest extends Specification {

    def "given an input file of tetris pieces, we generate an output file with the correct grid heights"() {
        given:
            String inputFileName = 'input.txt'
            File inputFile = getFileFromResourcesDirectory(inputFileName)
            File outputFile = File.createTempFile("tetris-engine-output-", ".txt")

        and: 'get the expected contents for the output file'
            List<String> expectedOutput = getExpectedOutputForInputFile(inputFileName)

        when: 'we run the application'
            TetrisApplication.main([inputFile, outputFile] as String[])

        and: 'read in the contents of the output file'
            List<String> actualOutput = outputFile.readLines()

        then: 'it is the correct number of lines'
            actualOutput.size() == expectedOutput.size()

        and: 'matches the expected output line-by-line'
            [actualOutput, expectedOutput].transpose().each { actual, expected ->
                assert actual == expected
            }

        cleanup:
            TestingUtils.deleteQuietly(outputFile)
    }

    private static File getFileFromResourcesDirectory(String filename) {
        return new File(ClassLoader.systemClassLoader.getResource(filename).toURI())
    }

    private static List<String> getExpectedOutputForInputFile(String inputFileName) {
        switch(inputFileName) {
            case 'input.txt':
                return ["2", "4", "0", "2", "4", "1", "0", "2", "2", "2", "1", "1", "4", "3", "1", "2", "1", "8", "8", "0", "3"]

            default:
                throw new RuntimeException("No output results found for file '" + inputFileName + "'")
        }
    }
}
