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
        GUILD_ID_HERE to "."
    ),
    guilds = listOf(
        GUILD_ID_HERE
    )
)
```

Let's break that down:
 - `api`: Your JDA instance
 - `globalMessagePrefix`: For message commands, the prefix that is accepted bot-wide, unless another prefix is set for a guild
 - `prefixGuildMap`: A map of guild (server) IDs to their own prefix. This will bypass`globalMessagePrefix`
 - `guilds` : A list of guild (server) IDs to set the guild-only commands to. (See [Registering Commands](#registering-commands))


## Commands

Zeus creates and registers commands via objects and annotations (`@Annotation`). To create a command, you must create an object and inherrit from `Command`:

```kotlin
import org.bspoones.zeus.core.command.Command

object MyCommand: Command() {}
```

### Registering commands

Once you have created your command object, head over to your main bot declaration class and add the object to the command registry:

```kotlin
// See setup guide above

CommandRegistry.registerCommands(
    MyCommand::class,
    guildOnly = true
)
```

Let's break that down:
- `MyCommand::class` class of your command, this is a `vararg` input so you can add as many command objects as you want, seperated by a comma (`,`)
- `guildOnly` default to `false`, this will decide if all commands you make are guild-only (they will register at a guild level). This is really useful for personal bots or for testing purposes, as commands are registered instantly. Set to `false` (or don't set it at all) to register all commands globally. This may take a few minutes to register.

### Creating a Slash Command

Slash commands are registered via the `@SlashCommand` annotation. This will register and map the function to a slash command:

```kotlin
object MyCommand: Command() {
    
    @SlashCommand("ping","Send a pong to the chat")
    fun onNameCommand(
        event: SlashCommandInteractionEvent
    ) {
        event.reply("Pong!").queue()
    }
}
```

As long as you have registered the parent object (in this case `MyCommand`), any `@SlashCommand` function will automatically be registered in Zeus' [Command Registery](src/main/kotlin/org/bspoones/zeus/command/CommandRegistry.kt).

**IMPORTANT**: Each Slash command <u>**MUST**</u> have a name and a description. The name must be lowercase. Here are the following character limits for a Slash Command:

| Type        | Max length |
|-------------|------------|
| Name        | 32         |
| Description | 100        |


### Creating a Message Command

Message commands are registered via the `@MessageCommand` annotation. This will register and map the function to custom registry that hooks onto Message Received Events, allowing for more customisation.:

```kotlin
object MyCommand: Command() {
    
    @MessageCommand("ping","Send a pong to the chat")
    fun onNameCommand(
        event: MessageReceivedEvent
    ) {
        event.channel.sendMessage("Pong!").queue()
    }
}
```

As long as you have registered the parent object (in this case `MyCommand`), any `@MessageCommand` function will automatically be registered in Zeus' [Command Registery](src/main/kotlin/org/bspoones/zeus/command/CommandRegistry.kt).


**IMPORTANT**: Each Message Command <u>**MUST**</u> have a name, and will be the string that is checked whenever a message is sent with a guid's chosen or set prefix

### Creating a User Context Command

User Context commands are registered via the `@UserContextCommand` annotation. This will register and map the function to custom registry that hooks onto User Context Interaction Events.

To use a User Context command, right click any user that shares a server with your bot and select `Apps`.

```kotlin
object MyCommand: Command() {
    
    @UserContextCommand("ping")
    fun onNameCommand(
        event: UserContextInteractionEvent
    ) {
        event.reply("Pong!").queue()
    }
}
```

As long as you have registered the parent object (in this case `MyCommand`), any `@UserContextCommand` function will automatically be registered in Zeus' [Command Registery](src/main/kotlin/org/bspoones/zeus/command/CommandRegistry.kt).


**IMPORTANT**: Each User Context Command <u>**MUST**</u> have a name. This will appear in the context menu when you right-click a user >> `App`

### Creating a Message Context Command

Message Context commands are registered via the `@MessageContextCommand` annotation. This will register and map the function to custom registry that hooks onto Message Context Interaction Events.

To use a Message Context command, right click any message and select `Apps`.

```kotlin
object MyCommand: Command() {
    
    @MessageContextCommand("ping")
    fun onNameCommand(
        event: MessageContextInteractionEvent
    ) {
        event.reply("Pong!").queue()
    }
}
```

As long as you have registered the parent object (in this case `MyCommand`), any `@MessageContextCommand` function will automatically be registered in Zeus' [Command Registery](src/main/kotlin/org/bspoones/zeus/command/CommandRegistry.kt).


**IMPORTANT**: Each User Context Command <u>**MUST**</u> have a name. This will appear in the context menu when you right-click a message >> `App`

### Creating a Slash Command Group



### GuildOnly

### NSFW

### Command Choices

#### StringChoice
#### DoubleChoice
#### LongChoice

### Variable Command Choices

#### VariableStringChoice
#### VariableDoubleChoice
#### VariableLongChoice

## Components

### AutoButton

### AutoEmbed

### AutoModal

### AutoSelectMenu



Made by BSpoones 🥄


