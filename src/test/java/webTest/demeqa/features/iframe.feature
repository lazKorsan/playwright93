

  Feature:


    @IfarmeBasic
    Scenario:
      Given demoqa kullanicisi "https://demoqa.com/browser-windows" sayfasina gider
      Then demoga kullanicisi newTab butonuna basar ve acilan pencerede cikan yaziyi consola yazdiririr
      When demoga kullanicisi newWindow butonuna basar ve acilan pencerede cikan yaziyi consola yazdiririr
      And demoga kullanicisi newWindowMessage butonuna basar ve acilan pencerde cikan yaziyi consola yazdirir
      Then demoga kullanicisi testi bitirir