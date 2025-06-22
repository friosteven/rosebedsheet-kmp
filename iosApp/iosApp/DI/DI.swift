//
//  DI.swift
//  iosApp
//
//  Created by John Steven Frio on 6/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared

@propertyWrapper
struct Injected<Dependency> {
    
    private let keyPath: KeyPath<DIHelper, Dependency>
    
    var wrappedValue: Dependency {
        DIHelper.shared[keyPath: keyPath]
    }
    
    init(_ keyPath: KeyPath<DIHelper, Dependency>) {
        self.keyPath = keyPath
    }
    
}
