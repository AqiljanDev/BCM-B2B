//
//  AppDelegate.swift
//  iosApp
//
//  Created by NetLight Lab on 05.09.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        KoinKt.doInitKoinIos()
        return true
    }
}
