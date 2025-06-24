//
//  ProjectNetworkEnvironmentURL.swift
//  Rose Bedsheet
//
//  Created by John Steven Frio on 6/10/25.
//

import Foundation
import Common

//initialize different network here
class ProjectNetworkConfig {
    static let shared = ProjectNetworkConfig()
    
    static let defaultHTTPClient = shared.getSupabaseHTTPClient()
    
    func getSupabaseHTTPClient() -> URLSessionHTTPClient {
        let supabaseEnv = NetworkEnvironmentURL(
            development: "http://192.168.5.151:54321/rest/v1",
            staging: "",
            production: ""
        )
        
        let supabaseConfig = NetworkConfiguration(environmentURL: supabaseEnv)
        let supabaseHTTPClient = URLSessionHTTPClient(configuration: supabaseConfig)
        
        return supabaseHTTPClient
    }
}
