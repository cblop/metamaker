(ns metagator.db)

(def default-db
  {:name "MetaMaker"
   :dataset-name ""
   ;; :fname "http://mist.cs.bath.ac.uk/refit-cleaned/CLEAN_House1.csv"
   ;; :fname "/home/_mthom/repos/metagator/resources/local/test.csv"
   :fname ""
   :dataset ""
   :cat-qa [nil]
   :cat-qb [nil]
   :filtered-cats [[]]
   :cat-as [{:id "time" :label "Time"}
            {:id "type" :label "Reading Type"}
            {:id "location" :label "Location"}
            {:id "value" :label "Value"}
            ]
   :cat-bs [{:id 0 :cat-id "time" :label "Start Time" :p "ssn:startTime"}
            {:id 1 :cat-id "time" :label "End Time" :p "ssn:endTime"}
            {:id 2 :cat-id "time" :label "Timestamp" :p "xsd:dateTimeStamp"}
            {:id 3 :cat-id "location" :label "Device" :p "dm4t:device"}
            {:id 4 :cat-id "location" :label "Sensor" :p "dm4t:sensor"}
            {:id 5 :cat-id "location" :label "Room" :p "dm4t:room"}
            {:id 6 :cat-id "location" :label "House" :p "dm4t:house"}
            {:id 7 :cat-id "location" :label "Appliance" :p "dm4t:appliance"}
            {:id 8 :cat-id "value" :label "Value" :p "ssn:hasValue"}
            {:id 9 :cat-id "value" :label "Maximum Value" :p "dm4t:maxValue"}
            {:id 10 :cat-id "value" :label "Minimum Value" :p "dm4t:minValue"}
            {:id 11 :cat-id "value" :label "Mean Value" :p "dm4t:meanValue"}
            {:id 12 :cat-id "type" :label "Power (Watts)" :p "dm4t:powerReading"}
            {:id 13 :cat-id "type" :label "Humidity (relative)" :p "dm4t:humidityReading"}
            {:id 14 :cat-id "type" :label "Gas" :p "dm4t:gasReading"}
            {:id 15 :cat-id "type" :label "CO2" :p "dm4t:co2Reading"}
            {:id 16 :cat-id "type" :label "Light" :p "dm4t:lightReading"}
            {:id 17 :cat-id "type" :label "Motion (PIR)" :p "dm4t:motionReading"}
            ]
   :sparql "PREFIX csv:<http://www.ntnu.no/ub/data/csv#>
PREFIX ssn:<http://purl.oclc.org/NET/ssnx/ssn#>
PREFIX xsd:<http://www.w3.org/2001/XMLSchema#>

SELECT *
WHERE {{
  SELECT ?f ?i WHERE {
       ?f csv:hasColumn ?c .
       ?c csv:mapsTo ssn:hasValue .
       ?c csv:hasIndex ?i .}
} UNION {
  SELECT ?ti WHERE {
       ?f csv:hasColumn ?t .
       ?t csv:mapsTo xsd:dateTimeStamp .
       ?t csv:hasIndex ?ti .}}}"
;;    :sparql "PREFIX csv:<http://www.ntnu.no/ub/data/csv#>
;; PREFIX ssn:<http://purl.oclc.org/NET/ssnx/ssn#>

;; SELECT ?f ?i
;; WHERE {?f csv:hasColumn ?c .
;;        ?c csv:mapsTo ssn:hasValue .
;;        ?c csv:hasIndex ?i .}"
   :files []
   :current-tab :tab1
   :description ""
   :selected-tab 0
   :datatypes [{:cat-b "Start time"
                :cat-a "Time"
                :type :string
                :p "ssn:startTime"
                }
               {:cat-b "End time"
                :cat-a "Time"
                :type :string
                :p "ssn:endTime"}
               {:cat-b "Timestamp"
                :cat-a "Time"
                :type :string
                :p "xsd:dateTimeStamp"}
               {:cat-b "Device"
                :cat-a "Location"
                :type :string
                :p "dm4t:device"}
               {:cat-b "Sensor"
                :cat-a "Location"
                :type :string
                :p "dm4t:sensor"}
               {
                :cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "dm4t:room"}
               {:cat-b "House"
                :cat-a "Location"
                :type :string
                :p "dm4t:house"
                }
               {
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "dm4t:appliance"}
               {:cat-b "Value"
                :cat-a "Value"
                :type :float
                :p "ssn:hasValue"}
               {:cat-b "Maximum Value"
                :cat-a "Value"
                :type :float
                :p "dm4t:maxValue"}
               {:cat-b "Minimum Value"
                :cat-a "Value"
                :type :float
                :p "dm4t:minValue"}
               {:cat-b "Mean Value"
                :cat-a "Value"
                :type :float
                :p "dm4t:meanValue"}
               {:cat-b "Power (Watts)"
                :cat-a "Type"
                :type :float
                :p "dm4t:powerReading"}
               {:cat-b "Humidity (relative)"
                :cat-a "Type"
                :type :float
                :p "rdf:a"
                :o "dm4t:humidityReading"}
               {:cat-b "Gas"
                :cat-a "Type"
                :type :float
                :p "dm4t:gasReading"}
               {:cat-b "CO2"
                :cat-a "Type"
                :type :float
                :p "dm4t:co2Reading"}
               {:cat-b "Light"
                :cat-a "Type"
                :type :float
                :p "dm4t:lightReading"}
               {:cat-b "Motion (PIR)"
                :cat-a "Type"
                :type :float
                :p "dm4t:motionReading"}
               ]
   :metas []
   :file-metas []
   :types [{:id 0 :label "String"} {:id 1 :label "Number"} {:id 2 :label "Date"}]
   }
  )
