package dev.aaa1115910.biliapi.entity.ugc

enum class UgcType(val rid: Int, val codename: String, val locId: Int = -1) {
    Douga(1, "douga", 4973),
    DougaMad(24, "mad"),
    DougaMmd(25, "mmd"),
    DougaHandDrawn(47, "handdrawn"),
    DougaVoice(257, "voice"),
    DougaGarageKit(210, "garage_kit"),
    DougaTokusatsu(86, "tokusatsu"),
    DougaAcgnTalks(253, "acgntalks"),
    DougaOther(27, "other"),

    Game(4, "game", 4991),
    GameStandAlone(17, "stand_alone"),
    GameESports(171, "esports"),
    GameMobile(172, "mobile"),
    GameOnline(65, "online"),
    GameBoard(173, "board"),
    GameGmv(121, "gmv"),
    GameMusic(136, "music"),
    GameMugen(19, "mugen"),

    Kichiku(119, "kichiku", 5004),
    KichikuGuide(22, "guide"),
    KichikuMad(26, "mad"),
    KichikuManualVocaloid(126, "manual_vocaloid"),
    KichikuTheatre(216, "theatre"),
    KichikuCourse(127, "course"),

    Music(3, "music", 4979),
    MusicOriginal(28, "original"),
    MusicLive(29, "live"),
    MusicCover(31, "cover"),
    MusicPerform(31, "perform"),
    MusicCommentary(243, "commentary"),
    MusicVocaloidUtau(30, "vocaloid"),
    MusicMv(193, "mv"),
    MusicFanVideos(266, "fan_videos"),
    MusicAiMusic(265, "ai_music"),
    MusicRadio(267, "radio"),
    MusicTutorial(244, "tutorial"),
    MusicOther(130, "other"),

    Dance(129, "dance", 4985),
    DanceOtaku(20, "otaku"),
    DanceHiphop(198, "hiphop"),
    DanceStar(199, "star"),
    DanceChina(200, "china"),
    DanceGestures(255, "gestures"),
    DanceThreeD(154, "three_d"),
    DanceDemo(156, "demo"),

    Cinephile(181, "cinephile", 5008),
    CinephileCinecism(182, "cinecism"),
    CinephileNibtage(183, "montage"),
    CinephileMashup(260, "mashup"),
    CinephileAiImagine(259, "ai_imaging"),
    CinephileTrailerInfo(184, "trailer_info"),
    CinephileShortPlay(85, "shortplay"),
    CinephileShortFilm(256, "shortfilm"),
    CinephileComperhensive(261, "comprehensive"),

    Ent(5, "ent", 5007),
    EntTalker(241, "talker"),
    EntCpRecommendation(262, "cp_recommendation"),
    EntBeauty(263, "beauty"),
    EntFans(242, "fans"),
    EntEntertainmentNews(264, "entertainment_news"),
    EntCelebrity(137, "celebrity"),
    EntVariety(71, "variety"),

    Knowledge(36, "knowledge", 4997),
    KnowledgeScience(201, "science"),
    KnowledgeSocialScience(124, "social_science"),
    KnowledgeHumanity(228, "humanity_history"),
    KnowledgeBusiness(207, "business"),
    KnowledgeCampus(208, "campus"),
    KnowledgeCareer(209, "career"),
    KnowledgeDesign(229, "design"),
    KnowledgeSkill(122, "skill"),

    Tech(188, "tech", 4998),
    TechDigital(95, "digital"),
    TechApplication(230, "application"),
    TechComputerTech(231, "computer_tech"),
    TechIndustry(232, "industry"),
    TechDiy(233, "diy"),

    Information(202, "information", 5005),
    InformationHotspot(203, "hotspot"),
    InformationGlobal(204, "global"),
    InformationSocial(205, "social"),
    InformationMultiple(206, "multiple"),

    Food(211, "food", 5002),
    FoodMake(76, "make"),
    FoodDetective(212, "detective"),
    FoodMeasurement(213, "measurement"),
    FoodRural(214, "rural"),
    FoodRecord(215, "record"),

    Life(160, "life", 5001),
    LifeFunny(138, "funny"),
    LifeParenting(254, "parenting"),
    LifeTravel(250, "travel"),
    LiseRuralLife(251, "rurallife"),
    LifeHome(239, "home"),
    LifeHandMake(161, "handmake"),
    LifePainting(162, "painting"),
    LifeDaily(21, "daily"),

    Car(223, "car", 5000),
    CarKnowledge(258, "knowledge"),
    CarStrategy(227, "strategy"),
    CarNewEnergyVehicle(247, "newenergyvehicle"),
    CarRacing(245, "racing"),
    CarModifiedVehicle(246, "modifiedvehicle"),
    CarMotorcycle(240, "motorcycle"),
    CarTouringCar(248, "touringcar"),
    CarLife(176, "life"),

    Fashion(155, "fashion", 5006),
    FashionMakeup(157, "makeup"),
    FashionCos(252, "cos"),
    FashionClothing(158, "clothing"),
    FashionCatwalk(159, "catwalk"),

    Sports(234, "sports", 4999),
    SportsBasketball(235, "basketball"),
    SportsFootball(249, "football"),
    SportsAerobics(164, "aerobics"),
    SportsAthletic(236, "athletic"),
    SportsCulture(237, "culture"),
    SportsComprehensive(238, "comprehensive"),

    Animal(217, "animal", 5003),
    AnimalCat(218, "cat"),
    AnimalDog(291, "dog"),
    AnimalReptiles(222, "reptiles"),
    AnimalWildAnima(221, "wild_animal"),
    AnimalSecondEdition(220, "second_edition"),
    AnimalComposite(75, "animal_composite")

}