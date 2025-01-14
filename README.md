CryptoArbitrageCalculator/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── nova/
│   │   │   │   │   ├── CryptoArbitrageCalculator/
│   │   │   │   │   │   ├── arbitrage/             
│   │   │   │   │   │   ├── prediction/            # New module for price prediction
│   │   │   │   │   │   │   ├── BinanceDataFetcher.java
│   │   │   │   │   │   │   ├── DataPreprocessor.java
│   │   │   │   │   │   │   ├── PricePredictor.java
│   │   │   │   │   │   │   ├── BacktestingEngine.java
│   │   │   │   │   │   │   ├── AIModelTrainer.java
│   │   │   │   │   │   │   ├── utils/             # Helper classes for prediction
│   │   │   │   │   │   │   │   ├── ChartGenerator.java
│   │   │   │   │   │   │   │   ├── CSVHandler.java
│   │   │   │   │   │   │   │   ├── MathUtils.java
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── web/                   # Web controller and views
│   │   │   │   │   │       ├── PredictionController.java
│   │   │   │   │   │       ├── templates/
│   │   │   │   │   │           ├── predictions.html
│   │   │   │   │   │           ├── charts.html
│   │   │   │   │
│   │   ├── resources/
│   │       ├── application.properties           
│   │       ├── static/
│   │       │   ├── css/
│   │       │   ├── js/
│   │       │   ├── images/
│   │
├── pom.xml                                    
└── README.md                                    

