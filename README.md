# Game Lobby Project

## Contributors
- Taras Dushenko - [GitHub Profile](https://github.com/dusenkot)
- Mateusz Cislo - [GitHub Profile](https://github.com/Kaellji)
- Ostap Maksymiv - [GitHub Profile](https://github.com/OstapMaksymiv)

## Overview

The Game Lobby Project is a mobile application developed for Android devices, featuring two classic games: Tic-Tac-Toe and Snake. This project was created for university purposes and was developed by three contributors: Taras Dushenko, Mateusz Cislo, and Ostap Maksymiv. Below is an overview of the project's features, setup instructions, and usage guidelines.

## Features

- **Tic-Tac-Toe**: A classic game where players take turns marking spaces in a 3x3 grid, aiming to align three of their marks in a row.
- **Snake**: A timeless game where players control a snake to eat food, causing it to grow longer while avoiding collisions with the walls and the snake's own body.
- **Game Lobby**: A centralized interface from which users can choose to start either of the games.

## Project Structure

The project is organized as follows:

- **MainLobby**: The main activity where users can select between the Tic-Tac-Toe and Snake games.
- **GameActivity**: Activity for the Tic-Tac-Toe game.
- **GameActivitySnake**: Activity for the Snake game.
- **AndroidManifest.xml**: Contains the application's metadata, including activities and themes.
- **res/drawable**: Directory for drawable resources, including game assets and icons.
- **res/layout**: Directory for layout XML files defining the UI structure for activities.

## Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-repository/game-lobby.git
   cd game-lobby
2.Open the Project:
  Open Android Studio and select "Open an existing project." Navigate to the cloned repository directory and open it.

3.Build the Project:
  In Android Studio, click on **Build > Rebuild Project** to compile the project and generate the APK.

4.Run the Application:
  1. Connect an Android device or start an emulator.
  2. Click on **Run > Run 'app'** to install and run the application on the selected device.

## Usage
- Start the Application: Launch the application from the device's app drawer. You will be greeted with the Game Lobby screen.
- Select a Game:
  - **Tic-Tac-Toe**: Tap on the Tic-Tac-Toe option to start the game. Follow the on-screen instructions to play.
  - **Snake**: Tap on the Snake option to start the game. Use the on-screen controls to navigate the snake and collect food.

## Configuration
### Changing the App Icon
1. Prepare Icons:
   - Ensure you have prepared icons in various sizes for different screen densities.
2. Update Icons:
   - In Android Studio, go to **File > New > Image Asset**.
   - Choose **Launcher Icons (Adaptive and Legacy)** and configure the icon settings.
   - Replace existing icons in `res/mipmap-*` directories.
3. Build the APK:
   - Rebuild the project to include the new icons in the APK.

## Contribution
If you would like to contribute to the project, please follow these steps:

1. Fork the repository on GitHub.
2. Create a new branch for your changes.
3. Make your modifications and commit them.
4. Submit a pull request with a detailed description of your changes.


