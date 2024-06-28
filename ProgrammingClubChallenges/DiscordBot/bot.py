import os
import random
import discord
from discord.ext import commands
from dotenv import load_dotenv


load_dotenv()
TOKEN = os.getenv('DISCORD_TOKEN')
GUILD = os.getenv('DISCORD_GUILD')
intents = discord.Intents.default()
intents.message_content = True

bot = commands.Bot(command_prefix='!', intents=intents)

@bot.event
async def on_ready():
    await bot.wait_until_ready()
    guild = discord.utils.find(lambda g: g.name == GUILD, bot.guilds)
    print(f'{bot.user} is connected to:\n'
          f'{guild.name} (id: {guild.id})')
    members = '\n - '.join([member.name for member in guild.members])
    print(f'Guild members:\n - {members}')
    await load_cogs()

@bot.event
async def on_member_join(member):
    await member.create_dm()
    await member.dm_channel.send(
        f'Hi {member.name}, welcome to the Lincoln Math Club Server! Let an @officer know if you have any questions.'
    )

@bot.event
async def on_message(message):
    if message.author == bot.user:  # prevent recursion (bot responds to its own message)
        return

    if 'happy birthday' in message.content.lower():
        response = random.choice(["happy bday math clubian!", "la multi ani!", "joyeuse anniversaire!"])
        await message.channel.send(response)
    elif 'competition' in message.content.lower():
        response = "math competitions are vital to enrich the minds of the young children"
        await message.channel.send(response)
    elif 'questions' in message.content.lower():
        response = 'why have questions when you could have answers?'
        await message.channel.send(response)
    elif message.content == 'raise-exception':
        raise discord.DiscordException

    await bot.process_commands(message)

@bot.command(name='create-channel')
@commands.has_role('officer')
async def create_channel(ctx, channel_name='default-channel-name'):
    guild = ctx.guild
    existing_channel = discord.utils.get(guild.channels, name=channel_name)
    if not existing_channel:
        print(f'Creating a new channel: {channel_name}')
        await guild.create_text_channel(channel_name)

@bot.event
async def command_error(ctx, error):
    if isinstance(error, commands.errors.CheckFailure):
        await ctx.send('You do not have the correct role for this command')

@bot.event
async def on_error(event, *args, **kwargs):
    with open('err.log', 'a') as f:
        if event == 'on_message':
            f.write(f'Unhandled message: {args[0]}\n')
        else:
            raise

async def load_cogs():
    await bot.wait_until_ready()
    await bot.load_extension('cogs.MathCommands')
    await bot.load_extension('cogs.ScienceCommands')

if __name__ == '__main__':
    bot.run(TOKEN)
