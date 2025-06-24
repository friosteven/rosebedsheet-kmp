//
//  HomeView.swift
//  Rose Bedsheet
//
//  Created by John Steven Frio on 6/3/25.
//

import SwiftUI
import Common

struct HomeView: View {
    @State private var isShowingSellerDashboardView = false
    @State private var isShowingProductDetailView = false
    
    var body: some View {
        VStack(spacing: 32) {
            headerView
            buttonStackView
            
            HStack(spacing: 4) {
                Text("Already have an account?")
                    .applyTypography(.bodyRegularLeading)
                    .foregroundStyle(AppColors.secondaryText)
                    .fixedSize()
                AppButton("Sign in", config: .init(style: .textOnly)) {
                }
                .fixedSize()
            }
        }
        .padding(.horizontal, 16)
        .fullScreenCover(isPresented: $isShowingSellerDashboardView, content: {
            SellerDashboardView()
        })
        .fullScreenCover(isPresented: $isShowingProductDetailView, content: {
            ProductDetailView()
        })
    }
    
    private var headerView: some View {
        VStack(spacing: 24) {
            BaseImage(systemImageName: "bed.double.circle.fill")
                .foregroundStyle(AppColors.primary)
                .frame(width: 100, height: 100)
            
            VStack(spacing: 16) {
                Text("Welcome to \(Constants.app_name)")
                    .applyTypography(.largeTitleBoldCenter)
                
                Text("Choose how you'd like to use the app")
                    .applyTypography(.title3RegularCenter)
                    .foregroundStyle(AppColors.secondary)
            }
        }
    }
    
    private var buttonStackView: some View {
        VStack {
            let config = AppCellTypographyConfiguration(
                titleTypography: .bodySemiboldLeading,
                titleColor: AppColors.primaryText,
                subTitleTypography: .bodyRegularLeading,
                subtitleColor: AppColors.secondaryText)
            
            AppCell(
                title: "Become a Seller",
                subTitle: "List and sell your bedsheets, pillowcases and blankets",
                typographyConfig: config,
                leftView: {
                    Image(systemName: "storefront")
                        .resizable()
                        .frame(width: 30, height: 30)
                        .foregroundColor(AppColors.primary)
                },
                rightView: {
                    Image(systemName: "chevron.right")
                        .foregroundColor(.gray)
                }
            ).onTapGesture {
                isShowingSellerDashboardView = true
            }
            
            AppCell(
                title: "Become a Buyer",
                subTitle: "Browse and shop quality bedsheets, pillowcases and blankets",
                typographyConfig: config,
                leftView: {
                    Image(systemName: "handbag")
                        .resizable()
                        .frame(width: 30, height: 30)
                        .foregroundColor(AppColors.primary)
                },
                rightView: {
                    Image(systemName: "chevron.right")
                        .foregroundColor(.gray)
                }
            )
        }
    }
}

#Preview {
    HomeView()
}

