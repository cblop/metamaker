(ns metagator.db)

(def datatypes
  [{:label "Start time"
    :parent "Time"
    :type :string
    :rdf "seas:measurementStart"}
   {:label "End time"
    :parent "Time"
    :type :string
    :rdf "seas:measurementEnd"}
   {:label "Timestamp"
    :parent "Time"
    :type :string
    :rdf "xsd:dateTimeStamp"}
   {:label "Device"
    :parent "Location"
    :type :string
    :rdf "seas:measurementSite"}
   {:label "Sensor"
    :parent "Location"
    :type :string
    :rdf "seas:measurementInstrument"}
   {:label "Room"
    :parent "Location"
    :type :string
    :rdf "seas:Room"}
   {:label "House"
    :parent "Location"
    :type :string
    :rdf "seas:House"}
   {:label "Appliance"
    :parent "Location"
    :type :string
    :rdf "seas:Appliance"}
   {:label "Value"
    :parent "Value"
    :type :float
    :rdf "seas:value"}
   {:label "Maximum Value"
    :parent "Value"
    :type :float
    :rdf "dm4t:maxValue"}
   {:label "Minimum Value"
    :parent "Value"
    :type :float
    :rdf "dm4t:minValue"}
   {:label "Mean Value"
    :parent "Value"
    :type :float
    :rdf "dm4t:meanValue"}
   {:label "Power (Watts)"
    :parent "Type"
    :type :float
    :rdf "seas:PowerQuantity"}
   {:label "Humidity (relative)"
    :parent "Type"
    :type :float
    :rdf "seas:relativeHumidity"}
   {:label "Gas"
    :parent "Type"
    :type :float
    :rdf "dm4t:GasReading"}
   {:label "CO2"
    :parent "Type"
    :type :float
    :rdf "dm4t:Co2Reading"}
   {:label "Light"
    :parent "Type"
    :type :float
    :rdf "dm4t:LightReading"}
   {:label "Motion (PIR)"
    :parent "Type"
    :type :float
    :rdf "dm4t:MotionReading"}
   ])

(def default-db
  {:name "MetaMaker"
   :fname "http://mist.cs.bath.ac.uk/refit-cleaned/CLEAN_House1.csv"
   :description "Data collected as part of the REFIT project at the University of Strathclyde"
   :selected-tab 0
   :datatypes datatypes
   :metas []
   :types [{:id 0 :label "String"} {:id 1 :label "Number"} {:id 2 :label "Date"}]
   }
   )
