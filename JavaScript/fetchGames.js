// epic
$(".order-description").map((i, e) => e.innerText).toArray().join("\n")

// steam
[...document.querySelectorAll(".gameListRowItemName.ellipsis")].map((e) => e.innerText).join("\n")

//gog
[...document.querySelectorAll(".product-title__text")].map((e) => e.innerText).join("\n")


