-- Seed catalogue for inventory-service.
--
-- Runs on every start, after Hibernate has built the schema
-- (spring.jpa.defer-datasource-initialization=true). ON CONFLICT DO NOTHING
-- keeps it idempotent, so an existing catalogue is never overwritten and only
-- an empty products table actually gets filled.

INSERT INTO inventory.products
    (id, name, category, price, number_in_stock, image_name, public_id, created_at)
VALUES
    (1, 'Classic Cotton T-Shirt', 'CLOTHING', 24.99, 10, 'classic-cotton-tshirt.png', 'a3d4c218-76e1-4f4c-9c9d-12d90e6378a1', '2026-08-21 16:06:26.824307+00'),
    (2, 'Minimalist Leather Backpack', 'ACCESSORIES', 79.99, 10, 'leather-packbag.png', '8b91fe72-d568-47da-90cc-a74c3f18c951', '2026-08-21 16:06:26.824307+00'),
    (3, 'Ceramic Coffee Mug', 'HOME', 14.5, 10, 'ceramic-coffee-mug.png', '25fa902c-39d5-42e2-a73c-c4079214bc92', '2026-08-21 16:06:26.824307+00'),
    (4, 'Wireless Headphones', 'ELECTRONICS', 129.99, 10, 'wireless-headphones.png', 'd78d3609-5fa4-4198-86e1-e55e58247529', '2026-08-21 16:06:26.824307+00'),
    (5, 'Linen Summer Shirt', 'CLOTHING', 44.9, 10, 'linen-summer-shirt.png', '62a5108d-f218-40d8-b330-f56b88fdd018', '2026-08-21 16:06:26.824307+00'),
    (6, 'Scented Soy Candle', 'HOME', 19.99, 10, 'scented-soy-candle.png', '97d28703-3fb5-4df9-b1b8-71bd9288cf83', '2026-08-21 16:06:26.824307+00'),
    (7, 'Retro Sunglasses', 'ACCESSORIES', 34.99, 10, 'retro-sunglasses.png', '3c1e5847-eaf2-40f7-9df8-5b62298912bc', '2026-08-21 16:06:26.824307+00'),
    (8, 'Portable Bluetooth Speaker', 'ELECTRONICS', 59.9, 10, 'portable-bluetooth-speaker.png', 'b691ce52-61bc-47e1-b39c-b6e34a012c91', '2026-08-21 16:06:26.824307+00'),
    (9, 'Canvas Sneakers', 'CLOTHING', 54.99, 10, 'canvas-sneakers.png', '1f52ca76-933e-4be8-b607-a8fa9783e645', '2026-08-21 16:06:26.824307+00'),
    (10, 'Wooden Desk Lamp', 'HOME', 69, 10, 'wooden-desk-lamp.png', 'e96c45ab-f6ca-492e-bba4-f9ae35467217', '2026-08-21 16:06:26.824307+00'),
    (11, 'Knitted Wool Cardigan', 'CLOTHING', 64.99, 10, 'knitted-wool-cardigan.png', '4d671c35-4ed7-4a91-bb7d-863a5517ec21', '2026-09-11 12:07:22.692469+00'),
    (12, 'Leather Crossbody Bag', 'ACCESSORIES', 74.9, 10, 'leather-crossbody-bag.png', '90c9ab12-d894-43de-95df-a1d46c277582', '2026-09-11 12:07:22.692469+00'),
    (13, 'Stoneware Serving Bowl', 'HOME', 29.99, 10, 'stoneware-serving-bowl.png', '3fa46551-4296-4d77-b151-98403691e716', '2026-09-11 12:07:22.692469+00'),
    (14, 'Compact Wireless Keyboard', 'ELECTRONICS', 69.99, 10, 'wireless-keyboard.png', '723b0df8-93ac-47b5-8538-6db118b56c41', '2026-09-11 12:07:22.692469+00'),
    (15, 'Relaxed Cotton Trousers', 'CLOTHING', 49.9, 10, 'cotton-trousers.png', '1b64cb21-cf76-4636-bbf0-6cce5b79e245', '2026-09-11 12:07:22.692469+00'),
    (16, 'Minimalist Wrist Watch', 'ACCESSORIES', 89.99, 10, 'minimalist-watch.png', 'ef7352c4-5d70-470e-8bc1-d65d26a50329', '2026-09-11 12:07:22.692469+00'),
    (17, 'Woven Throw Blanket', 'HOME', 39.99, 10, 'woven-throw-blanket.png', '69f10128-30a1-477e-987d-b928db7fc153', '2026-09-11 12:07:22.692469+00'),
    (18, 'Portable Table Fan', 'ELECTRONICS', 44.9, 10, 'portable-table-fan.png', 'b15266a9-f8ea-49cd-95a2-9df31244e710', '2026-09-11 12:07:22.692469+00'),
    (19, 'Canvas Tote Bag', 'ACCESSORIES', 24.5, 10, 'canvas-tote-bag.png', 'c44134fa-11cc-48d7-a676-36dbde3bd908', '2026-09-11 12:07:22.692469+00'),
    (20, 'Linen Cushion Cover', 'HOME', 19.9, 10, 'linen-cushion-cover.png', '5a885312-604e-40cb-a1ae-c3e4722fc667', '2026-09-11 12:07:22.692469+00')
ON CONFLICT (id) DO NOTHING;

SELECT setval(
    pg_get_serial_sequence('inventory.products', 'id'),
    (SELECT COALESCE(MAX(id), 1) FROM inventory.products)
);
