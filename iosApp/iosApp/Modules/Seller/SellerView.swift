//
//  SellerView.swift
//  iosApp
//
//  Created by John Steven Frio on 6/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SellerView: View {

    @StateObject private var viewModel: SellerViewModel = SellerViewModel()
    
    var body: some View {
        VStack {
            if viewModel.isLoading {
                ProgressView()
            } else if let error = viewModel.error {
                Text("Error: \(error)")
                    .foregroundColor(.red)
            } else {
                List(viewModel.colors, id: \.id) { color in
                    Text(color.name)
                }
            }
        }
        .onAppear(perform: viewModel.fetchColors)
        .navigationTitle("Colors")
    }
}

#Preview {
    SellerView()
}
