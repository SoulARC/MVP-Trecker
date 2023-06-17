<h1>Tucan Tournament MVP Calculator</h1>
<p>This project implements a program to calculate the Most Valuable Player (MVP) of the Tucan Tournament. The program is capable of processing data for various sports, including basketball and handball.</p>

<h2>Project Description</h2>
<p>Tucan Tournament is a tournament where multiple players compete in different sports disciplines. The goal of this project is to develop a program that calculates the MVP based on the game statistics.</p>
<p>The program receives CSV files, each containing the statistics of a single game. Each file includes player data, team information, and game results. The program determines the MVP by analyzing the players' rating points and other performance indicators across multiple games.</p>

<h2>Features</h2>
<p>The program provides the following features:</p>
<ul>
<li>Calculation of the MVP of the tournament based on game statistics in various sports.</li>
<li>Determination of the player with the highest rating points as the MVP.</li>
<li>Support for different sports, including basketball, handball, and the ability to extend for additional sports.</li>
</ul>

<h2>Usage</h2>
<p>To use the program, follow these steps:</p>
<ol>
<li>Invoke the <code>gameStrategy(String filePath)</code> method from the <code>GameResultProcessor</code> class and provide the file path as an argument to process the game results.</li>
<li>Call the <code>getMVP()</code> method to obtain the MVP player.</li>
</ol>
<pre><code>// Example usage
String filePath = "path/to/game_results.csv";
GameResultProcessor gameProcessor = new GameResultProcessor();
gameProcessor.gameStrategy(filePath);

// Example usage of the getMVP() method MVPService mvpService\mvpService.getMVP();
System.out.println("MVP player is: " + getMVP.getNickname());
System.out.println("Score: " + getMVP.getRatingPoints);
</code></pre>

<h2>Extension</h2>
<p>The project can be extended to support additional sports and functionalities. To do so, new classes and methods can be added to handle game statistics for specific sports.</p>
