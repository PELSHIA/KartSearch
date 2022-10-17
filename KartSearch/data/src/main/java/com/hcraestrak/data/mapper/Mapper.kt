package com.hcraestrak.data.mapper

import com.hcraestrak.data.local.model.BookmarkEntity
import com.hcraestrak.data.local.model.SearchEntity
import com.hcraestrak.data.remote.model.*
import com.hcraestrak.domain.model.local.Bookmark
import com.hcraestrak.domain.model.local.Search
import com.hcraestrak.domain.model.remote.*
import kotlin.math.sin

fun mapperToBookmarkList(bookmarkEntityList: List<BookmarkEntity>): List<Bookmark> {
    return bookmarkEntityList.toList().map {
        Bookmark(
            it.nickName
        )
    }
}

fun mapperToBookmarkEntity(bookmark: Bookmark): BookmarkEntity {
    return BookmarkEntity(
        bookmark.nickName
    )
}

fun mapperToSearchList(searchEntityList: List<SearchEntity>): List<Search> {
    return searchEntityList.toList().map {
        Search(
            it.word
        )
    }
}

fun mapperToSearchEntity(search: Search): SearchEntity {
    return SearchEntity(
        search.word
    )
}

fun mapperToUserInfo(userInfoEntity: UserInfoEntity): UserInfo? {
    return UserInfo(
        userInfoEntity.accessId,
        userInfoEntity.name,
        userInfoEntity.level
    )
}

fun mapperToMatch(matchEntity: MatchEntity): Match {
    return Match(
        mapperToMatchesList(matchEntity.matches),
        matchEntity.nickName
    )
}

fun mapperToMatchesList(matches: List<MatchesEntity>): List<Matches> {
    return matches.toList().map {
        Matches (
            it.matchType,
            mapperToMatchInfoList(it.matches)
        )
    }
}

fun mapperToMatchInfoList(matchInfoEntity: List<MatchInfoEntity>): List<MatchInfo> {
    return matchInfoEntity.toList().map {
        MatchInfo (
            it.accountNo,
            it.channelName,
            it.character,
            it.endTime,
            it.matchId,
            it.matchResult,
            it.matchType,
            mapperToPlayer(it.player),
            it.playerCount,
            it.seasonType,
            it.startTime,
            it.teamId,
            it.trackId
        )
    }
}

private fun mapperToPlayer(playerEntity: PlayerEntity): Player {
    return Player (
        playerEntity.accountNo,
        playerEntity.character,
        playerEntity.characterName,
        playerEntity.flyingPet,
        playerEntity.kart,
        playerEntity.license,
        playerEntity.matchRank,
        playerEntity.matchRetired,
        playerEntity.matchTime,
        playerEntity.matchWin,
        playerEntity.partsEngine,
        playerEntity.partsHandle,
        playerEntity.partsKit,
        playerEntity.partsWheel,
        playerEntity.pet,
        playerEntity.rankinggrade2
    )
}

private fun mapperToPlayerList(playerEntity: List<PlayerEntity>): List<Player> {
    return playerEntity.toList().map {
        Player(
            it.accountNo,
            it.character,
            it.characterName,
            it.flyingPet,
            it.kart,
            it.license,
            it.matchRank,
            it.matchRetired,
            it.matchTime,
            it.matchWin,
            it.partsEngine,
            it.partsHandle,
            it.partsKit,
            it.partsWheel,
            it.pet,
            it.rankinggrade2
        )
    }
}

fun mapperToDetailSingle(singleEntity: DetailSingleEntity): DetailSingle {
    return DetailSingle (
        singleEntity.matchId,
        singleEntity.matchType,
        singleEntity.matchResult,
        singleEntity.gameSpeed,
        singleEntity.startTime,
        singleEntity.endTime,
        singleEntity.playTime,
        singleEntity.channelName,
        singleEntity.trackId,
        mapperToPlayerList(singleEntity.players)
    )
}

fun mapperToDetailTeam(teamEntity: DetailTeamEntity): DetailTeam {
    return DetailTeam(
        teamEntity.matchId,
        teamEntity.matchType,
        teamEntity.matchResult,
        teamEntity.gameSpeed,
        teamEntity.startTime,
        teamEntity.endTime,
        teamEntity.playTime,
        teamEntity.channelName,
        teamEntity.trackId,
        mapperToTeamList(teamEntity.teams)
    )
}

private fun mapperToTeamList(teamEntityList: List<TeamEntity>): List<Team> {
    return teamEntityList.toList().map {
        Team (
            it.teamId,
            mapperToPlayerList(it.players)
        )
    }
}
