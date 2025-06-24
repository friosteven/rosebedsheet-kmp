//
//  SellerCreateListingView.swift
//  Rose Bedsheet
//
//  Created by John Steven Frio on 6/7/25.
//

import SwiftUI
import Common

struct SellerCreateListingView: View {
    
    struct Dependencies: EquatableHashableStruct {
        let id = UUID()
        
        var onTapSaveAsDraft: ((String) -> Void)?
        var onTapSubmit: (() -> Void)?
    }
    
    @State private var productNameText = ""
    @State private var productDescriptionText = ""
    @State private var selectedProductType: String = ""
    @State private var productTypes: [String] = ["Bedsheet", "Pillowcases"]
    
    @State private var priceText = ""
    @State private var quantityText = ""
    
    @StateObject private var viewModel = SellerViewModel()
    
    var dependencies: Dependencies
    
    init(dependencies: Dependencies) {
        self.dependencies = dependencies
    }
    
    var body: some View {
        Group {
            if viewModel.isLoading {
                ProgressView()
            } else {
                VStack {
                    AppContainerView(title: "Create Listing",
                                     config: .navbarConfig,
                                     content: {
                        VStack(spacing: 24) {
                            AppPhotosPicker(title: "Product photos",
                                            styleConfig: .default,
                                            cardStyleConfig: .default,
                                            displayMode: .dontReplacePlaceHolder(limit: 12)) {
                                
                            }
                            AppTextField(
                                text: $productNameText,
                                placeholder: "Product Name",
                                placeholderStyle: .floating)
                            
                            AppTextField(
                                text: $productDescriptionText,
                                placeholder: "Description",
                                type: .multiline(maxLength: 500),
                                placeholderStyle: .floating)
                            
                            AppListView(viewModel.categoriesWithSizesModel,
                                        selectionMode: .singleSelect,
                                        scrollDirection: .horizontal,
                                        showsIndicators: false,
                                        sectionTitle: "Category") { value, selectedItems, isSelected in
                                
                                VStack {
                                    Text(value.name)
                                        .applyTypography(isSelected ? .bodySemiboldLeading : .bodyRegularLeading)
                                        .foregroundStyle(isSelected ? AppColors.surface : AppColors.primaryText)
                                        .pad(horizontal: 16, vertical: 8)
                                }
                                .background(isSelected ? AppColors.primary : AppColors.border)
                                .cornerRadius(24, corners: .allCorners)
                                .cardStyle(isSelected ? .selected : .nonSelected)
                                .pad(horizontal: 4, vertical: 12)
                                .onChange(of: selectedItems, perform: { value in
                                    if let selectedCategory = value.first {
                                        viewModel.setSizesBasedOnSelectedCategory(selectedCategory: selectedCategory)
                                    }
                                })
                            }
                            
                            AppListView(viewModel.sizesBasedOnSelectedCategory,
                                        selectionMode: .singleSelect,
                                        scrollDirection: .horizontal,
                                        showsIndicators: false,
                                        sectionTitle: "Size") { value, selectedItems, isSelected in
                                VStack {
                                    Text(value.name)
                                        .applyTypography(isSelected ? .bodySemiboldLeading : .bodyRegularLeading)
                                        .foregroundStyle(isSelected ? AppColors.surface : AppColors.primaryText)
                                        .pad(horizontal: 16, vertical: 8)
                                }
                                .background(isSelected ? AppColors.primary : AppColors.border)
                                .cornerRadius(24, corners: .allCorners)
                                .cardStyle(isSelected ? .selected : .nonSelected)
                                .pad(horizontal: 4, vertical: 12)
                            }
                            
                            AppFlowLayoutView(viewModel.materialsModel,
                                              selectionMode: .singleSelect,
                                              verticalSpacing: 0,
                                              sectionTitle: "Material") { value, selectedItems, isSelected in
                                VStack {
                                    Text(value.name)
                                        .applyTypography(isSelected ? .bodySemiboldLeading : .bodyRegularLeading)
                                        .foregroundStyle(isSelected ? AppColors.surface : AppColors.primaryText)
                                        .pad(horizontal: 16, vertical: 8)
                                }
                                .background(isSelected ? AppColors.primary : AppColors.border)
                                .cornerRadius(24, corners: .allCorners)
                                .cardStyle(isSelected ? .selected : .nonSelected)
                                .pad(horizontal: 4, vertical: 8)
                            }
                            
                            AppListView(viewModel.colorsModel,
                                        selectionMode: .multiSelect(limit: 5),
                                        scrollDirection: .horizontal,
                                        showsIndicators: false,
                                        sectionTitle: "Color") { value, selectedItems, isSelected in
                                Circle()
                                    .fill(Color(hex: value.hex))
                                    .frame(width: 48, height: 48)
                                    .cardStyle(isSelected ? .selected : .nonSelected)
                                    .pad(horizontal: 4, vertical: 12)
                            }
                            
                            AppFlowLayoutView(viewModel.designsModel,
                                              selectionMode: .singleSelect,
                                              verticalSpacing: 0,
                                              sectionTitle: "Design Type") { value, selectedItems, isSelected in
                                VStack {
                                    Text(value.name)
                                        .applyTypography(isSelected ? .bodySemiboldLeading : .bodyRegularLeading)
                                        .foregroundStyle(isSelected ? AppColors.surface : AppColors.primaryText)
                                        .pad(horizontal: 16, vertical: 8)
                                }
                                .background(isSelected ? AppColors.primary : AppColors.border)
                                .cornerRadius(24, corners: .allCorners)
                                .cardStyle(isSelected ? .selected : .nonSelected)
                                .pad(horizontal: 4, vertical: 8)
                            }
                            
                            AppTextField(
                                text: $priceText,
                                placeholder: "Price",
                                placeholderStyle: .floating)
                            
                            AppTextField(
                                text: $quantityText,
                                placeholder: "Stock Quantity",
                                placeholderStyle: .floating)
                        }
                        .pad(horizontal: 16, vertical: 20)
                    })
                    
                    
                    HStack(spacing: 16) {
                        AppButton("Save as draft", config: .init(style: .outlineAffirm)) {
                            dependencies.onTapSaveAsDraft?("Save as draft pressed")
                        }
                        
                        AppButton("Submit") {
                            dependencies.onTapSubmit?()
                            //                    AppAlert.show(title: "hehehe")
                        }
                    }
                    .frame(height: 48)
                    .pad(horizontal: 16, vertical: 16)
                }
                .background(AppColors.inputBackground)
            }
        }
        .task {
            viewModel.observeAllStates()
            viewModel.fetchAll()
        }
        .onDisappear {
            viewModel.clearPresenter()
        }
    }
}

#Preview {
    SellerCreateListingView(dependencies: SellerCreateListingView.Dependencies())
}
