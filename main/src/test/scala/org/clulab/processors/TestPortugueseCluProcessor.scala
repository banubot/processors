package org.clulab.processors

import org.clulab.processors.clu.PortugueseCluProcessor
import org.scalatest.{FlatSpec, Matchers}

class TestPortugueseCluProcessor extends FlatSpec with Matchers {
  val proc = new PortugueseCluProcessor()

  "PortugueseCluProcessor" should "tokenize raw text correctly" in {
    val doc = proc.mkDocument("Da Silva viajou para a China. Lá, ele visitou Pequim.")
    doc.clear()

    doc.sentences(0).words(0) should be ("De")
    doc.sentences(0).words(1) should be ("a")
    doc.sentences(0).words(2) should be ("Silva")
    doc.sentences(0).words(3) should be ("viajou")
    doc.sentences(0).words(4) should be ("para")
    doc.sentences(0).words(5) should be ("a")
    doc.sentences(0).words(6) should be ("China")
    doc.sentences(0).words(7) should be (".")
    doc.sentences(1).words(0) should be ("Lá")
    doc.sentences(1).words(1) should be (",")
    doc.sentences(1).words(2) should be ("ele")
    doc.sentences(1).words(3) should be ("visitou")
    doc.sentences(1).words(4) should be ("Pequim")
    doc.sentences(1).words(5) should be (".")

    doc.sentences(0).startOffsets(0) should be (0)
    doc.sentences(0).startOffsets(1) should be (0)
    doc.sentences(0).startOffsets(2) should be (3)
    doc.sentences(0).startOffsets(3) should be (9)
    doc.sentences(0).startOffsets(4) should be (16)
    doc.sentences(0).startOffsets(5) should be (21)
    doc.sentences(0).startOffsets(6) should be (23)
    doc.sentences(0).startOffsets(7) should be (28)
    doc.sentences(1).startOffsets(0) should be (30)
    doc.sentences(1).startOffsets(1) should be (32)
    doc.sentences(1).startOffsets(2) should be (34)
    doc.sentences(1).startOffsets(3) should be (38)
    doc.sentences(1).startOffsets(4) should be (46)
    doc.sentences(1).startOffsets(5) should be (52)
    println("Tokenization is fine.")
  }

  it should "POS tag correctly" in {
    val doc = proc.mkDocument("Da Silva viajou para a China. Lá, ele visitou Pequim.")
    proc.lemmatize(doc)
    proc.tagPartsOfSpeech(doc)
    doc.clear()

    doc.sentences(0).tags.get(0) should be ("ADP")
    doc.sentences(0).tags.get(1) should be ("DET")
    doc.sentences(0).tags.get(2) should be ("PROPN")
    doc.sentences(0).tags.get(3) should be ("VERB")
    doc.sentences(0).tags.get(4) should be ("ADP")
    doc.sentences(0).tags.get(5) should be ("DET")
    doc.sentences(0).tags.get(6) should be ("PROPN")
    doc.sentences(0).tags.get(7) should be ("PUNCT")
    doc.sentences(1).tags.get(0) should be ("ADV")
    doc.sentences(1).tags.get(1) should be ("PUNCT")
    doc.sentences(1).tags.get(2) should be ("PRON")
    doc.sentences(1).tags.get(3) should be ("VERB")
    doc.sentences(1).tags.get(4) should be ("PROPN")
    doc.sentences(1).tags.get(5) should be ("PUNCT")
    println("POS tagging is fine.")
  }

/*
  TODO: chunks
  it should "recognize syntactic chunks correctly" in {
    val doc = proc.mkDocument("")
    proc.lemmatize(doc)
    proc.tagPartsOfSpeech(doc)
    proc.chunking(doc)
    doc.clear()

    doc.sentences(0).chunks.get(0) should be ("B-NP")
    doc.sentences(0).chunks.get(1) should be ("B-VP")
    doc.sentences(0).chunks.get(2) should be ("B-NP")
    doc.sentences(0).chunks.get(3) should be ("I-NP")
    doc.sentences(0).chunks.get(4) should be ("I-NP")
    doc.sentences(0).chunks.get(5) should be ("I-NP")
    doc.sentences(0).chunks.get(6) should be ("B-VP")
    doc.sentences(0).chunks.get(7) should be ("I-VP")
    doc.sentences(0).chunks.get(8) should be ("B-PP")
    doc.sentences(0).chunks.get(9) should be ("B-NP")
    doc.sentences(0).chunks.get(10) should be ("I-NP")
    doc.sentences(0).chunks.get(11) should be ("I-NP")
  }
  TODO: lemmatization
  it should "lemmatize text correctly" in {
    val doc = proc.mkDocument("John Doe went to the shops.")
    proc.lemmatize(doc)
    doc.clear()

    doc.sentences(0).lemmas.get(0) should be ("john")
    doc.sentences(0).lemmas.get(2) should be ("go")
    doc.sentences(0).lemmas.get(5) should be ("shop")
    println("Lemmatization is fine.")
  }
*/

  it should "parse text correctly" in {
    val doc = proc.annotate("João da Silva viajou para a China.")

    //println("Basic universal dependencies:")
    //println(doc.sentences.head.universalBasicDependencies.get)

    doc.sentences.head.universalBasicDependencies.get.hasEdge(4, 0, "nsubj") should be(true)
    doc.sentences.head.universalBasicDependencies.get.hasEdge(4, 7, "nmod") should be(true)
    doc.sentences.head.universalBasicDependencies.get.hasEdge(7, 5, "case") should be(true)
    doc.sentences.head.universalBasicDependencies.get.hasEdge(7, 6, "det") should be(true)
    println("Parsing is fine.")
  }
}