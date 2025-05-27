TCSS 360 Group Proejct
Authors: Lily Hoopes, Komalpreet Dhaliwal, Christiannel Maningat
1UP Trivia Maze


Welcome to the Trivia Maze, a puzzle-based game where knowledge is your key to escape! Navigate through a maze of rooms, each guarded by a trivia question. Answer correctly to pass through; fail, and the door is locked forever. Will you make it to the exit?

🧩 Project Description
This game challenges the player to escape a maze by answering trivia questions. Each door between rooms is locked with a question. Players must use their knowledge in multiple-choice, true/false, and short answer formats to advance.

If too many doors get locked, the player may find themselves trapped... forever (or until they restart)!

💡 Features: 
 - 🚪 Maze navigation (minimum 4x4 grid) -
 - ❓ Questions stored in a SQLite database
 - 🧠 Multiple question
 - 🔐 Doors lock permanently if a question is answered incorrectly
 - 💾 Save and load game state (via serialization)
 - 🖼️ MVC architecture (Model-View-Controller)
 - 🧭 Dynamic interface showing:
    - Current room and maze state
    - Navigation controls (based on available doors)
    - Trivia questions with appropriate input formats

 📜 Menu system with:
  - File → Save Game, Load Game, Exit
  - Help → About, Instructions
