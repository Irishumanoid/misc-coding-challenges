import urllib.request as libreq
import urllib.parse
import json
import random
import aionewton

def get_nasa_media(query, keywords='', media_type='', max_retries=50):
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
                with libreq.urlopen(collection_dir) as files:
                    collection_json = json.loads(files.read())
                    url = str(collection_dir).replace('collection.json', collection_dir.split('/')[-2])
                    if entry_type == 'image':
                        url += '~large.jpg'
                    elif entry_type == 'video':
                        url += '~small.mp4'
                    elif entry_type == 'audio':
                        url += '~orig.mp3'
                    print(url)
            except IOError as e:
                print('Error generating query, regenerating')
                if max_retries > 0:
                    get_nasa_media(query, keywords, media_type, max_retries - 1)
    except IOError as e:
        print(f'URL could not be parsed: {e}')

