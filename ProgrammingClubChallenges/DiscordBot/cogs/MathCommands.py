from discord.ext import commands
import random
import json
from enum import Enum
import urllib.request as libreq
import json


def get_text_between_substrings(text, first, second):
    chunks = text.split(first)
    final_segs = []
    for chunk in chunks[1:]:
        if second in chunk:
            final_segs.append(chunk.split(second)[0].strip())
    return final_segs


def generate_query_arxiv(max_results, input):
    args_str = ':'
    params = input.split(',')
    if len(params) > 1:
        args_str += '+AND+all:'.join([param for param in params])
    else:
        args_str += params[0]
    print(args_str + '\n\n\n\n')
    with libreq.urlopen(f'http://export.arxiv.org/api/query?search_query=all{args_str}&start=0&max_results={max_results}') as url:
        r = url.read()
        article_title = get_text_between_substrings(str(r), '<title>', '</title>')
        article_info = get_text_between_substrings(str(r), '<summary>', '</summary>') 
        link = get_text_between_substrings(str(r), '<link title="pdf" href="', '" rel="related" type="application/pdf"/>')

        all_info = ''
        for i in range(len(article_info)):
            concat_info = article_title[i] + '\n' + '-'*30 + '\n' + article_info[i] + '\n' + '-'*30 + '\n' + link[i] + '\n'
            all_info += concat_info + '\n\n\n'
        
        print(all_info)
        return all_info

async def query_math_engine(ctx, expr, type):
    try:
        root = f'https://newton.vercel.app/api/v2/{type}/'+expr
        with libreq.urlopen(root) as url:
            res = json.loads(url.read().decode())['result']
            await ctx.send(res)
    except SyntaxError as e:
        await ctx.send(f'Your expression input is invalid, please try again: {e}')

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

    @commands.command(name='get_academic_paper', help='get a paper rec by providing the number of recs you want and an array of strings of your prefered topics')
    async def get_paper_rec(self, ctx, num_results, pref_list):
        await ctx.send(f'Here is your paper recommendation {ctx.author.name} \n' + generate_query_arxiv(num_results, pref_list))

    @commands.command(name='simplify_expr', help='pass in expression you would like to simplify')
    async def simplify_expression(self, ctx, expr):
        await query_math_engine(ctx, expr, 'simplify')

    @commands.command(name='derive', help='take the derivative of an expression')
    async def differentiate_expression(self, ctx, expr):
        await query_math_engine(ctx, expr, 'derive')

    @commands.command(name='integrate', help='take the integral of an expression')
    async def integrate_expression(self, ctx, expr):
        await query_math_engine(ctx, expr, 'integrate')

    @commands.command(name='factor', help='factor an expression')
    async def integrate_expression(self, ctx, expr):
        await query_math_engine(ctx, expr, 'factor')
    
    @commands.command(name='find_zeros', help='find the zeros of an expression')
    async def integrate_expression(self, ctx, expr):
        await query_math_engine(ctx, expr, 'zeros')
    
    @commands.command(name='tangent', help='find the tangent line to a line at a point: [point]|[expr] like 2|x^2')
    async def integrate_expression(self, ctx, expr):
        await query_math_engine(ctx, expr, 'tangent')
    
    @commands.command(name='area_under_curve', help='find the area under a curve: [start:end]|[expr] like [2:4]|x^2')
    async def integrate_expression(self, ctx, expr):
        await query_math_engine(ctx, expr, 'area')


async def setup(bot):
    await bot.add_cog(MathCommands(bot))
