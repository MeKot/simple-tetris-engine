package te.tetris.core.io


import spock.lang.Specification
import spock.lang.Subject

class InputOutputFileHandlerTest extends Specification {

    File inputFile = File.createTempFile("tetris-engine-file-handler-input-", ".txt")
    File outputFile = File.createTempFile("tetris-engine-file-handler-output-", ".txt")

    @Subject
    InputOutputFileHandler fileHandler = [inputFile, outputFile]

    def setup() {
        inputFile.withWriter { writer ->
            writer.writeLine("line1")
            writer.writeLine("line2")
            writer.writeLine("line3")
        }
    }

    def cleanup() {
        inputFile.delete()
        outputFile.delete()
    }

    def "input is read line-by-line, passed through a function, then written as output"() {
        expect: 'the output file to be empty initially'
            outputFile.readLines() == []

        when: 'we process the input file'
            List<String> inputFileContents = []
            fileHandler.processLineFromInput({ String input ->
                inputFileContents << input

                return input + "-" + input
            })

        then: 'input file was read line-by-line'
            inputFileContents[0] == "line1"
            inputFileContents[1] == "line2"
            inputFileContents[2] == "line3"

        and: 'output file was written to correctly'
            List<String> outputFileContents = outputFile.readLines()
            outputFileContents.size() == 3
            outputFileContents[0] == "line1-line1"
            outputFileContents[1] == "line2-line2"
            outputFileContents[2] == "line3-line3"
    }

}
