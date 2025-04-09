import pyautogui as pg
import time

#abrir chrome
pg.press('win')
pg.write('chrome')
pg.press('enter')
time.sleep(0.5)

#digitar site
pg.write('https://dlp.hashtagtreinamentos.com/python/intensivao/login')
pg.press('enter')
time.sleep(0.5)

#clicar email
pg.click(x=797, y=409)
time.sleep(0.5)
pg.write('test@test.com')
time.sleep(0.2)

#clicar senha
pg.press('tab')
time.sleep(0.2)
pg.write('test123')
time.sleep(0.2)

#logar
pg.press('tab')
pg.press('enter')

time.sleep(1.3)
pg.click(x=1146, y=380)

time.sleep(0.2)
pg.click(x=732, y=293)

#importar base de dados
import pandas as pd

tabela = pd.read_csv('produtos.csv')

for linha in tabela.index:
    
    cod = tabela.loc[linha, 'codigo']
    pg.write(cod)
    pg.press('tab')

    marca = tabela.loc[linha, 'marca']
    pg.write(marca)
    pg.press('tab')
    time.sleep(0.1)

    tipo = tabela.loc[linha, 'tipo']
    pg.write(tipo)
    pg.press('tab')
    time.sleep(0.1)

    categoria = tabela.loc[linha, 'categoria']
    pg.write(str(categoria))
    pg.press('tab')
    time.sleep(0.1)

    preco_unitario = tabela.loc[linha, 'preco_unitario']
    pg.write(str(preco_unitario))
    pg.press('tab')
    time.sleep(0.3)  # Longer delay for numeric fields

    custo = tabela.loc[linha, 'custo']
    pg.write(str(custo))
    pg.press('tab')
    time.sleep(0.3)  # Longer delay for numeric fields

    obs = tabela.loc[linha, 'obs']
    if pd.isna(obs):
        pg.write('')
    else:
        pg.write(obs)

    pg.press('tab')
    pg.press('enter')
    time.sleep(0.1)

    pg.scroll(10000)
    time.sleep(1)
    pg.click(x=732, y=293)        