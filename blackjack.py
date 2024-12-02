import random, sys

HEARTS = chr(9829)
DIAMONDS = chr(9830)
SPADES = chr(9824)
CLUBS = chr(9827)

values = ['A','2','3','4','5','6','7','8','9','10','J','Q','K']
suits = [HEARTS, DIAMONDS, SPADES, CLUBS]

deck = []
for value in values:
    for suit in suits:
        deck.append((value, suit))

#playerHands = []
playerHand = []
dealerHand = []


wager = 0

def main():
    money = 10000

    print('Welcome to blackjack! You will be given two cards, and on your turn, the choice to hit or stand.')
    print("Hit: Pick up another card.\tStand: Keep the cards you have.")
    print("Aim for the value of your hand to be higher than the house, but don't go over 21!"+'\n')
    print('You start the game with 10000 dollars, try to make a profit!')
    
    #print('Enter player count:')
    playerCount = 1 #int(input('>'))
    #for i in range(playerCount):
        
        #playerHand = []
        #playerHands.append(playerHand)



    play = True
    while(play):
        endRound = False

        for i in range(playerCount):
            print("Player {}, how much would you like to wager? You can also type 'stop' to exit out.".format(i+1))
            loop = True
            while loop == True:
                wager = input('> ')
                try:
                    wager = int(wager)
                    if wager <= money and wager >= 0:
                        loop = False
                    if wager > money:
                        print('Please enter a lower number.')
                    if wager < 0:
                        print('Wager must be a positive number.')
                except ValueError:
                    if wager == 'stop':
                        sys.exit()
                    print('Please enter an integer.')
            print('You have wagered {} dollars.'.format(wager))

        
        print('\nThe dealers cards are:')
        moveCards(deck, 2, dealerHand)
        displayCards(dealerHand, True)

        total, ace = addCards(dealerHand)
        if ace and total + 10 == 21:
            print('Dealer has a blackjack! Round over.')
            for i in range(playerCount):
                gameOver(False, money, wager)
            continue

        for i in range(playerCount):
            print("\nPlayer {}'s cards:".format(i+1))
            moveCards(deck, 2, playerHand)
            displayCards(playerHand, False)
        
        total, ace = addCards(playerHand)
        if ace and total + 10 <= 21:
            print('Your total: {}'.format(total) + '|{}'.format((total)+10))
        elif ace and total + 10 > 21:
            print('Your total: {}'.format(total))
        else:
            print('Your total: {}'.format(total))

        #Player turn
        loop = True
        while loop == True:
            print("You can either hit or stand.")
            print('Type "hit" or "stand".')
            response = input('> ')
            
            if response == 'hit':
                moveCards(deck, 1, playerHand)
                displayCards(playerHand, False)

                #Total value of player's hand
                total, ace = addCards(playerHand)
                if ace and total + 10 <= 21:
                    print('Your total: {}'.format(total) + '|{}'.format((total)+10))
                elif ace and total + 10 > 21:
                    print('Your total: {}'.format(total))
                else:
                    print('Your total: {}'.format(total))

                if total > 21:
                        loop = False
                        print('Dealer wins; you have busted.')
                        gameOver(False, money, wager)
                        endRound = True
                    
            elif response == 'stand':
                loop = False
            else:
                print('Invalid response')
        if endRound == True:
            continue
        if ace and total + 10 <= 21:
            total += 10
        playerFinalTotal = total


        print('\n\nDealer playing...')
        loop = True
        while loop == True:
            print('\nThe dealers cards are:')
            displayCards(dealerHand, False)

            #Prints total value of cards in the dealer's hand
            total, ace = addCards(dealerHand)
            if ace and total + 10 <= 21:
                print('Total value: {}'.format(total) + '|{}'.format((total)+10))
            elif ace and total + 10 > 21:
                print('Total value: {}'.format(total))
            else:
                print('Total value: {}'.format(total))
                
            if total > 21:
                loop = False
                print('Dealer busts; you win!')
                gameOver(True, money, wager)
                endRound = True
                
            elif (ace == False and total < playerFinalTotal) or (ace == True and total + 10 < playerFinalTotal):
                moveCards(deck, 1, dealerHand)
            elif (ace == False and total >= playerFinalTotal) or (ace == True and total + 10 >= playerFinalTotal):
                loop = False
        if ace and total + 10 <= 21:
            total += 10
        dealerFinalTotal = total
        if endRound == True:
            continue

        print('\n\nFinal tally:')
        displayCards(playerHand, False)
        print('Your total: {}'.format(playerFinalTotal))
        
        displayCards(dealerHand, False)
        total, ace = addCards(dealerHand)
        print('Total value: {}'.format(dealerFinalTotal))
         
        if dealerFinalTotal >= playerFinalTotal:
            print('\nDealer has a higher sum!')
            gameOver(False, money, wager)
        if dealerFinalTotal < playerFinalTotal:
            print('\nYou have the higher sum!')
            gameOver(True, money, wager)




            

def moveCards(location, amount, receiver):
    
    for i in range(amount):
        receiver.append(location.pop(random.randint(0, len(location)-1)))



def displayCards(hand, hide):
    topL = []
    centerTopL = []
    centerBottomL = []
    bottomL = []
    top = ''
    centerTop = ''
    centerBottom = ''
    bottom = ''

    for card in hand:
        value, suit = card
        
        if value == '10':
            topL.append(' ___ ')
            centerTopL.append('|{} |'.format(value))
            centerBottomL.append('| {} |'.format(suit))
            bottomL.append('|_{}|'.format(value))
        else:
            topL.append(' ___ ')
            centerTopL.append('|{}  |'.format(value))
            centerBottomL.append('| {} |'.format(suit))
            bottomL.append('|__{}|'.format(value))

    if hide == True:
        topL[0] = ' ___ '
        centerTopL[0] = '|## |'
        centerBottomL[0] = '|###|'
        bottomL[0] = '|_##|'
        
    for i in range(len(hand)):
        top += topL[i]
        centerTop += centerTopL[i]
        centerBottom += centerBottomL[i]
        bottom += bottomL[i]

    print(top)
    print(centerTop)
    print(centerBottom)
    print(bottom)


def addCards(hand):
    total = 0
    ace = False
    
    for card in hand:
        value, suit = card
        
        if value == 'A':
            total += 1
            ace = True
        elif value == 'J':
            total += 10
        elif value == 'Q':
            total += 10
        elif value == 'K':
            total += 10
        else:
            total += int(value)
            
    return total, ace

def gameOver(winStatus, money, wager):
    if winStatus == True:
        money += wager
    else:
        money -= wager
    print('You now have {} dollars'.format(money))
    
    if money == 0:
        print('You have gone bankrupt. Good luck next time!')
        sys.exit()

    for i in range(1):
        moveCards(playerHand, len(playerHand), deck)
    moveCards(dealerHand, len(dealerHand), deck)




if __name__ == '__main__':
    main()
