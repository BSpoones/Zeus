# Zeus

Zeus is a multi-purpose JDA command framework designed to make creating bots easier!

# Contents
- [Setup](#setup)
- [Commands](#commands)
    - [Creating a Slash Command](#creating-a-slash-command)
    - [Creating a Message Command](#creating-a-message-command)
    - [Creating a User Context Command](#creating-a-user-context-command)
    - [Creating a Message Context Command](#creating-a-message-context-command)
    - [Creating a Slash Command Group](#creating-a-slash-command-group)
    - [Registering commands](#registering-commands)
- [Components](#components)
    - [AutoButton](#autobutton)
    - [AutoEmbed](#autoembed)
    - [AutoModal](#automodal)
    - [AutoSelectMenu](#autoselectmenu)

## Setup

Setting up Zeus is done via a setup function. Once you have created your discord bot, setup Zeus with the `setup()` method.

**Example:**
```kotlin
val jda = JDABuilder.createDefault("token").build()
Zeus.setup(
    api = jda,
    globalMessagePrefix = "!",
    prefixGuildMap = mutableMapOf(
        11111111 to "."
    ),
    guilds = listOf(
        11111111
    )
)
```

Let's break that down:
 - `api`: Your JDA instance
 - `globalMessagePrefix`: For message commands, the prefix that is accepted bot-wide, unless another prefix is set for a guild
 - `prefixGuildMap`: A map of guild (server) IDs to their own prefix. This will bypass`globalMessagePrefix`
 - `guilds` : A list of guild (server) IDs to set the guild-only commands to. (See [Registering Commands](#registering-commands))


## Commands

### Creating a Slash Command

### Creating a Message Command

### Creating a User Context Command

### Creating a Message Context Command

### Creating a Slash Command Group

### Registering commands

## Components

### AutoButton

### AutoEmbed

### AutoModal

### AutoSelectMenu



Made by BSpoones 🥄


