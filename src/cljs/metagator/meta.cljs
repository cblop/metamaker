(ns metagator.meta
  (:require [re-frame.core :as re-frame]))

(def CSV "http://www.ntnu.no/ub/data/csv#")
(def RDFS "http://www.w3.org/2000/01/rdf-schema#")
(def DC "http://purl.org/dc/terms/")
(def XSD "http://www.w3.org/2001/XMLSchema#")

(defn prefixer [name uri]
  (str "@prefix " name ":<" uri "> ."))

(defn column-triples [columns]
  (for [i (range (count columns))]
    (let [col (nth columns i)]
      (str ":column" i " a csv:Column ;\n"
           (if (:label col) (str "  rdfs:label \"" (:label col) "\" ;\n"))
           (apply str (for [m col]
                        (let [
                              p (re-frame/subscribe [:p-for-meta m])]
                          (if @p
                            (str "  csv:mapsTo " @p " ;\n")
                            m))))
           "  csv:hasIndex \"" i "\" .\n\n")
      )))

(defn make-triples [hmap]
  (let [csv (prefixer "csv" CSV)
        rdfs (prefixer "rdfs" RDFS)
        dcterms (prefixer "dcterms" DC)
        xsd (prefixer "xsd" XSD)
        seas (prefixer "seas" "https://w3id.org/seas#")
        ssn (prefixer "ssn" "http://purl.oclc.org/NET/ssnx/ssn#")]
    (apply str
           [csv "\n" rdfs "\n" dcterms "\n" xsd "\n" seas "\n" ssn "\n" "\n\n"
            (str "<" (:url hmap) "> a csv:CsvDocument ;\n")
            (str "  rdfs:label \"" (:name hmap) "\" ;\n")
            (str "  rdfs:comment \"" (:description hmap) "\" ;\n")
            (apply str
                   (for [i (range (count (:columns hmap)))]
                     (str "  csv:hasColumn :column" i (if (= i (- (count (:columns hmap)) 1)) " .\n\n" " ;\n"))))
            (apply str (column-triples (:columns hmap)))])
    ))

