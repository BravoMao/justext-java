package nl.wizenoze.justext.html

import spock.lang.Specification

import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

import static org.w3c.dom.Node.TEXT_NODE

/**
 * Created by lcsontos on 1/7/16.
 */
class HtmlBeautifierTest extends Specification {

    private final HtmlBeautifier htmlBeautifier = new HtmlBeautifier();

    def testAttributes() {
        def dirtyHtml = [
                '<?xml version="1.0" encoding="windows-1250"?>',
                '<!DOCTYPE html>',
                '<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sk" lang="sk">',
                '<body id="index">',
                '<img src="someImage" alt="this is the description of the image">',
                '</body>',
                '</html>'
        ].join()

        when:
        def cleanedHtml = htmlBeautifier.cleanHtml(dirtyHtml)
        println(cleanedHtml)

        then:
        cleanedHtml.equals([
                '<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sk" lang="sk">',
                '<body id="index">',
                '<img src="someImage" alt="this is the description of the image" />',
                '</body>',
                '</html>'
        ].join())
    }

    def testRemoveComments() {
        def dirtyHtml = "<html><!-- comment --><body><h1>Header</h1><!-- comment --> text<p>footer</body></html>"

        when:
        def cleanedHtml = htmlBeautifier.cleanHtml(dirtyHtml)

        then:
        cleanedHtml.equals("<html><body><h1>Header</h1> text<p>footer</p></body></html>")
    }

    def testRemoveHeadTag() {
        def dirtyHtml = [
            "<html><head><title>Title</title></head><body>",
            "<h1>Header</h1>",
            "<p><span>text</span></p>",
            "<p>footer <em>like</em> a boss</p>",
            "</body></html>"
        ].join()

        when:
        def cleanedHtml = htmlBeautifier.cleanHtml(dirtyHtml)
        println(cleanedHtml)

        then:
        cleanedHtml.equals(
                "<html><body><h1>Header</h1><p><span>text</span></p><p>footer <em>like</em> a boss</p></body></html>")
    }

    def testSimpleXhtmlWithDeclaration() {
        def dirtyHtml = [
            '<?xml version="1.0" encoding="windows-1250"?>',
            '<!DOCTYPE html>',
            '<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sk" lang="sk">',
            '<head>',
            '<title>Hello World</title>',
            '<meta http-equiv="imagetoolbar" content="no" />',
            '<meta http-equiv="Content-Type" content="text/html; charset=windows-1250" />',
            '</head>',
            '<body id="index">',
            '</body>',
            '</html>'
        ].join()

        when:
        def cleanedHtml = htmlBeautifier.cleanHtml(dirtyHtml)
        println(cleanedHtml)

        then:
        cleanedHtml.equals([
            '<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="sk" lang="sk">',
            '<body id="index">',
            '</body>',
            '</html>'
        ].join())
    }

}
