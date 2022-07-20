package com.github.peacetrue.learn.asciidoclet;

/**
 * = Asciidoclet
 *
 * Sample comments that include `source code`.
 *
 * [source,java]
 * --
 * public class Asciidoclet extends Doclet {
 *     private final Asciidoctor asciidoctor = Asciidoctor.Factory.create();
 *
 *     @SuppressWarnings("UnusedDeclaration")
 *     public static boolean start(RootDoc rootDoc) {
 *         new Asciidoclet().render(rootDoc);
 *         return Standard.start(rootDoc);
 *     }
 * }
 * --
 *
 * @author https://github.com/johncarl81[John Ericksen]
 */
public class LearnAsciidoclet {
}
