package te.tetris

import spock.lang.Specification

class EndToEndTest extends Specification {

    def "end-to-end test: given input.txt, output.txt contains the expected contents"() {
        given:
            File inputFile = new File(ClassLoader.systemClassLoader.getResource('input.txt').toURI())
            File outputFile = File.createTempFile("tetris-engine-output-", ".txt")
            String[] args = [inputFile, outputFile]*.absolutePath

        and: 'we clear the output file contents'
            outputFile.text = ''

        when: 'we process the input file'
            TetrisApplication.main(args)

        then: 'the output file contains the values'
            List<String> outputFileContents = outputFile.readLines()
            println outputFile
            println outputFileContents
            outputFileContents

        cleanup:
            TestingUtils.deleteQuietly(outputFile)
    }
}
