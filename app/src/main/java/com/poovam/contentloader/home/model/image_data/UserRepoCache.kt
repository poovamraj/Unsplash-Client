package com.poovam.githubdetails.userdetails.model

/**
 * Created by poovam-5255 on 8/5/2018.
 * This is the only object that should interact with Realm object repo
 */
//class UserRepoCache(private val userData: UserData) : Cache<ArrayList<UserRepoEntity>> {
//
//    //TODO Since this will open and close db connection many times implement a helper method that takes in arraylist and does it in a single go
//    override fun put(cacheObject: ArrayList<UserRepoEntity>) {
//        for (element in cacheObject){
//            Repo.Helper.writeUserRepoToDb(element.id,element.name,element.userName,element.fullName,element.url,
//                    element.isFork,element.starGazers,element.forkCount)
//        }
//    }
//
//
//    override fun get():  ArrayList<UserRepoEntity> {
//        val repos = Repo.Helper.getAllRepoWithUserName(userData.userName)
//        val userRepoEntities = ArrayList<UserRepoEntity>()
//        for (i in repos.indices) {
//            val temp = repos[i]
//            if(temp != null){
//                val repoEntity = UserRepoEntity(temp.userName,temp.id,temp.name,temp.fullName,temp.url,temp.isFork,temp.starGazersCount,temp.forkCount)
//                userRepoEntities.add(repoEntity)
//            }
//        }
//        return userRepoEntities
//    }
//
//}