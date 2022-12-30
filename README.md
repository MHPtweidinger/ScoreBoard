# Score Board

Simple Android application to note down scores for players on analogue games.

[![Android CI](https://github.com/MHPtweidinger/ScoreBoard/actions/workflows/android.yml/badge.svg)](https://github.com/MHPtweidinger/ScoreBoard/actions/workflows/android.yml)

## Screenshots

<image width ="200" src="https://media.githubusercontent.com/media/MHPtweidinger/ScoreBoard/main/screenshottests/src/test/snapshots/images/de.tobsinger.screenshottests_PreviewScreenshotTests_screenshotTests%5BDefault%20Group%20-%20ScoreboardLayoutPreview%2CPIXEL_5%5D.png"/> <image width ="200" src="https://media.githubusercontent.com/media/MHPtweidinger/ScoreBoard/main/screenshottests/src/test/snapshots/images/de.tobsinger.screenshottests_PreviewScreenshotTests_screenshotTests%5BDefault%20Group%20-%20UpdateScoreComposePreview%2CPIXEL_5_DARK%5D.png"/>

## Features

- Create players
- Add/subtract scored points per player in each round
- Delete a single player
- Delete all players
- Clear scores for all players


## Context 

This is a showcase implementation to demonstrate state of the art techniques to ensure quality for native Android applications. 

### Tests

#### Screenshot Tests
Screenshots are created for each screen with given reproducible states. These screenshots get compared on a pixel level to detect changes in the layout of the app. 
In this project screenshot tests are implemented using [paparazzi](https://github.com/cashapp/paparazzi).

#### Unit tests
- Isolated classes
- Mocked dependencies
- Inspect behaviour of class under test under controlled conditions


### Architecture
- MVVM Architecture pattern
- Jetpack Compose UI
- Material Design 3
- Jetpack Data Store
- Jetpack Compose Navigation




