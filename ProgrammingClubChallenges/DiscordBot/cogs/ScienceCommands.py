import io
from discord.ext import commands
import json
import urllib.request as libreq
import os
import json
import aiohttp
import discord
import random
import requests

class ScienceCommands(commands.Cog):
    def __init__(self, bot):
        self.bot = bot
    
    @commands.command(name='image_of_the_day', help='get a nice NASA image')
    async def get_nasa_image(self, ctx):
        api_key = os.getenv('NASA_API_KEY')
        with libreq.urlopen(f'https://api.nasa.gov/planetary/apod?api_key={api_key}') as url:
            im_json = json.loads(url.read())
            image_url = im_json['url']
            title = im_json['title']
            desc = im_json['explanation']

            async with aiohttp.ClientSession() as session:
                async with session.get(image_url) as resp: # gets image from url
                    img = await resp.read() 
                    with io.BytesIO(img) as file: #converts to file-like object
                        await ctx.send(f'{title} \n\n {desc}', file=discord.File(file, "testimage.png"))

    @commands.command(name='get_nasa_media', help='Provide a query to get NASA media. Optional: comma-separated keywords, a media type (image, video, audio)')
    async def get_nasa_media(self, ctx, query, keywords='', media_type='', max_retries=50):
        base_str = f'https://images-api.nasa.gov/search?q={query}'
        if keywords != '':
            base_str += f'&keywords={keywords}'
        if media_type != '':
            base_str += f'&media_type={media_type}'

        try:
            with libreq.urlopen(base_str) as url:
                contents_json = json.loads(url.read())
                with open('/Users/irislitiu/work/misc_coding_challenges/ProgrammingClubChallenges/DiscordBot/cogs/nasa.json', 'w+') as f:
                    json.dump(contents_json, f)

                entries = contents_json['collection']['items']
                rand_entry = entries[random.randint(0, len(entries)-1)]
                entry_type = rand_entry['data'][0]['media_type']
                collection_dir = rand_entry['href']

                print(collection_dir)
                print(f'\n media type is {entry_type}')
                try:
                    with libreq.urlopen(collection_dir):
                        media_url = str(collection_dir).replace('collection.json', collection_dir.split('/')[-2])
                        if entry_type == 'image':
                            media_url += '~large.jpg'

                            file_path = '/Users/irislitiu/work/misc_coding_challenges/ProgrammingClubChallenges/DiscordBot/cogs/test.jpg'
                            img_data = requests.get(media_url).content
                            with open(file_path, 'wb') as handler:
                                handler.write(img_data)

                            with open(file_path, 'rb') as file:
                                await ctx.send(file=discord.File(file))

                        elif entry_type == 'video':
                            media_url += '~small.mp4'
                            await ctx.send(f'you can find the video at {media_url}')
                        elif entry_type == 'audio':
                            media_url += '~orig.mp3'
                            await ctx.send(f'you can find the audio at {media_url}')
                        print(media_url)

                except IOError as e:
                    print('Error generating query, regenerating')
                    if max_retries > 0:
                        await self.get_nasa_media(ctx, query, keywords, media_type, max_retries - 1)

        except IOError as e:
            print(f'URL could not be parsed: {e}')


async def setup(bot):
    await bot.add_cog(ScienceCommands(bot))