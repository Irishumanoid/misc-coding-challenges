from datasets import load_dataset, DatasetDict

african_languages = {
    "en": "English",
    "lua": "Tshiluba",
    "lin": "Lingala",
    "swa": "Swahili",
    "hau": "Hausa",
    "zul": "Zulu",
    "yor": "Yoruba",
    "twi": "Twi",
    "ibo": "Igbo",
    "amh": "Amharic",
    "som": "Somali"
}

langs_nums = {}

def dataset_loader(dataset_name, *langs):
    if dataset_name == "tatoeba":
        return load_dataset(dataset_name, lang1=langs[0], lang2=langs[1], streaming=True)  # Only get metadata and don't actually download the dataset
    elif dataset_name == "ted_talks_iwslt":
        ted_sets = []
        for year in ["2014", "2015", "2016"]: #these are the years for which data exists
            ted_sets.append(load_dataset(dataset_name, language_pair=(langs[0], langs[1]), year=year))
        
        combined_dataset = {}
        for split in ted_sets[0].keys():
            combined_dataset[split] = ted_sets[0][split].train_test_split(test_size=0)['train']
            for i in range(1, len(ted_sets)):
                combined_dataset[split] = combined_dataset[split].concatenate(ted_sets[i][split].train_test_split(test_size=0)['train'])
        return DatasetDict(combined_dataset)
    elif dataset_name == "Helsinki-NLP/opus-100":
        sorted_langs = sorted([langs[0], langs[1]])
        return load_dataset(dataset_name, str(sorted_langs[0] + "-" + sorted_langs[1]))

def generate_language_data_two_langs(dataset_name, lang, second_lang):
    print(f'First lang is {lang} and second lang is {second_lang}')
    try:
        dataset = dataset_loader(dataset_name, lang, second_lang) 
        num_rows = sum(1 for _ in dataset['train'])
        
        val = african_languages.get(lang) + ", " + african_languages.get(second_lang)
        print(f'Number of rows for {val} is {num_rows}')
        langs_nums[val] = num_rows
    except FileNotFoundError:
        print(f"Dataset not found for {lang}-{second_lang} pair.")
    except Exception as e:
        print(f"An error occurred for {lang}-{second_lang} pair: {e}")

    print(langs_nums)
    with open(f'/Users/irislitiu/work/misc_coding_challenges/ProgrammingClubChallenges/NLP_minority_languages/{dataset_name}_dataset_langs.txt', 'a') as f:
        out = ''.join([lang + " : " + str(langs_nums[lang]) + '\n' for lang in langs_nums.keys()])
        f.write(out)

def multi_lang_gen():
    for lang in african_languages.keys():
        other_langs = [l for l in african_languages.keys() if l != lang]
        for second_lang in other_langs:
            generate_language_data_two_langs(dataset_name="Helsinki-NLP/opus-100", lang=lang, second_lang=second_lang)


ds = load_dataset('bible-nlp/biblenlp-corpus', streaming=True)['train']
freq_map = {}
for d in ds:
    if d['lang'] in freq_map.keys():
        freq_map[d['lang']] += 1
    else:
        freq_map[d['lang']] = 1

for key in freq_map.keys():
    lang = key
    freq = freq_map[key]
    print(f'language is {lang} with {freq} rows')