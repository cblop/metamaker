(ns metagator.db)

(def default-db
  {:name "MetaMaker"
   :dataset-name "refit-house"
   ;; :fname "http://mist.cs.bath.ac.uk/refit-cleaned/CLEAN_House1.csv"
   ;; :fname "/home/_mthom/repos/metagator/resources/local/test.csv"
   :fname ""
   :description "Data collected as part of the REFIT project at the University of Strathclyde"
   :selected-tab 0
   :datatypes [{:cat-b "Start time"
                :cat-a "Time"
                :type :string
                :p "seas:measurementStart"
                }
               {:cat-b "End time"
                :cat-a "Time"
                :type :string
                :p "seas:measurementEnd"}
               {:cat-b "Timestamp"
                :cat-a "Time"
                :type :string
                :p "xsd:dateTimeStamp"}
               {:cat-b "Device"
                :cat-a "Location"
                :type :string
                :p "seas:measurementSite"}
               {:cat-b "Sensor"
                :cat-a "Location"
                :type :string
                :p "seas:measurementInstrument"}
               {
                :cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "seas:measurementSite"}
               {:cat-b "House"
                :cat-a "Location"
                :type :string
                :p "seas:measurementSite"
                }
               {
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "seas:measurementSite"}
               {:cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "seas:measurementSite"
                }
               {:cat-b "House"
                :cat-a "Location"
                :type :string
                :p "seas:measurementSite"
                }
               {:cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "seas:measurementSite"
                }
               {:cat-b "Value"
                :cat-a "Value"
                :type :float
                :p "seas:value"}
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
