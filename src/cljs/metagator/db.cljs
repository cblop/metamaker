(ns metagator.db)

(def datatypes
  [{:cat-b "Start time"
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
   {:cat-c "Bedroom"
    :cat-b "Room"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Room"}
   {:cat-c "Bathroom"
    :cat-b "Room"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Room"}
   {:cat-c "Kitchen"
    :cat-b "Room"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Room"}
   {:cat-c "Living Room"
    :cat-b "Room"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Room"}
   {:cat-b "House"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:House"}
   {:cat-c "Washer Dryer"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Toaster"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Desktop Computer"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Kettle"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Microwave"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Dishwasher"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Television"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Fridge-Freezer"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Air Conditioner"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Electric Heater"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Air Conditioner"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Tumble Dryer"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Washing Machine"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Freezer"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
   {:cat-c "Fridge"
    :cat-b "Appliance"
    :cat-a "Location"
    :type :string
    :p "rdf:a"
    :o "seas:Appliance"}
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
    :p "rdf:a"
    :o "seas:PowerQuantity"}
   {:cat-b "Humidity (relative)"
    :cat-a "Type"
    :type :float
    :p "rdf:a"
    :o "seas:RelativeHumidity"}
   {:cat-b "Gas"
    :cat-a "Type"
    :type :float
    :p "rdf:a"
    :o "dm4t:GasReading"}
   {:cat-b "CO2"
    :cat-a "Type"
    :type :float
    :p "rdf:a"
    :o "dm4t:Co2Reading"}
   {:cat-b "Light"
    :cat-a "Type"
    :type :float
    :p "rdf:a"
    :o "dm4t:LightReading"}
   {:cat-b "Motion (PIR)"
    :cat-a "Type"
    :type :float
    :p "rdf:a"
    :o "dm4t:MotionReading"}
   ])

(def default-db
  {:name "MetaMaker"
   :dname "refit-house1"
   :fname "http://mist.cs.bath.ac.uk/refit-cleaned/CLEAN_House1.csv"
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
               {:cat-c "Bedroom"
                :cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Room"}
               {:cat-c "Bathroom"
                :cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Room"}
               {:cat-c "Kitchen"
                :cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Room"}
               {:cat-c "Living Room"
                :cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Room"}
               {:cat-b "House"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:House"}
               {:cat-c "Washer Dryer"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Toaster"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Desktop Computer"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Kettle"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Microwave"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Dishwasher"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Television"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Fridge-Freezer"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Air Conditioner"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Electric Heater"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Air Conditioner"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Tumble Dryer"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Washing Machine"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Freezer"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-c "Fridge"
                :cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
               {:cat-b "Value"
                :cat-a "Value"
                :type :float
                :p "seas:value"}
               {:cat-b "Room"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Room"}
               {:cat-b "House"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:House"}
               {:cat-b "Appliance"
                :cat-a "Location"
                :type :string
                :p "rdf:a"
                :o "seas:Appliance"}
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
                :p "rdf:a"
                :o "seas:PowerQuantity"}
               {:cat-b "Humidity (relative)"
                :cat-a "Type"
                :type :float
                :p "rdf:a"
                :o "seas:RelativeHumidity"}
               {:cat-b "Gas"
                :cat-a "Type"
                :type :float
                :p "rdf:a"
                :o "dm4t:GasReading"}
               {:cat-b "CO2"
                :cat-a "Type"
                :type :float
                :p "rdf:a"
                :o "dm4t:Co2Reading"}
               {:cat-b "Light"
                :cat-a "Type"
                :type :float
                :p "rdf:a"
                :o "dm4t:LightReading"}
               {:cat-b "Motion (PIR)"
                :cat-a "Type"
                :type :float
                :p "rdf:a"
                :o "dm4t:MotionReading"}
               ]
   :metas []
   :file-metas []
   :types [{:id 0 :label "String"} {:id 1 :label "Number"} {:id 2 :label "Date"}]
   }
  )
