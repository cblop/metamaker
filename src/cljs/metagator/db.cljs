(ns metagator.db)

(def default-db
  {:name "MetaMaker"
   :fname "http://mist.cs.bath.ac.uk/refit-cleaned/CLEAN_House1.csv"
   :description "Data collected as part of the REFIT project at the University of Strathclyde"
   :ftype 0
   :ftype2 0
   :ftype3 0
   :ftypes [{:id 0 :label "readings" :post "which are" :types [{:id 0 :label "in one location" :post "named"}
                                                               {:id 1 :label "measurements of one type" :post ":" :types [{:id 0 :label "Power"}
                                                                                                                          {:id 1 :label "Gas"}
                                                                                                                          {:id 2 :label "CO2"}
                                                                                                                          {:id 3 :label "Temperature"}]}]}
            {:id 1 :label "mappings"}]})
