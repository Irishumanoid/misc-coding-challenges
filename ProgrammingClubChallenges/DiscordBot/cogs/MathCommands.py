from discord.ext import commands
import random
import json
from enum import Enum

class MathCommands(commands.Cog):
    def __init__(self, bot):
        self.bot = bot

    class Level(Enum):
        EASY = 0,
        MEDIUM = 1,
        HARD = 2
    
    @commands.command(name='number_gen', help='generates random numbers given a min, max, and number of numbers to output')
    async def number_generator(self, ctx, min: int, max: int, num_nums: int):
        nums = [str(random.randint(min, max)) for _ in range(num_nums)]
        out = ', '.join(nums)
        await ctx.send(out)

    @commands.command(name='easy_math_gen', help='get a random arithmetic problem, you may pass in Level.EASY, Level.MEDIUM, or Level.HARD')
    async def easy_math_gen(self, ctx, level: str = None): 
        try:
            with open('/Users/irislitiu/work/misc-coding-challenges/ProgrammingClubChallenges/DiscordBot/simple_math_questions.json') as f:
                math_questions = json.load(f)
            
            poss_questions = []
            if level:
                print(f'Level provided: {level}')
                for question in math_questions:
                    if question['level'] == level.upper():
                        poss_questions.append(question)
            else:
                poss_questions = math_questions

            if poss_questions:
                rand_question = poss_questions[random.randint(0, len(poss_questions) - 1)]
                await ctx.send(rand_question['question'])
            else:
                await ctx.send("No questions available for the specified level.")
        except Exception as e:
            print(f'Error: {e}')
            await ctx.send('An error occurred while fetching the math question.')


async def setup(bot):
    await bot.add_cog(MathCommands(bot))
