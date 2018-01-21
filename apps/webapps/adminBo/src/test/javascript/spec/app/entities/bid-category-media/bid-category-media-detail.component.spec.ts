/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { BidCategoryMediaDetailComponent } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media-detail.component';
import { BidCategoryMediaService } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.service';
import { BidCategoryMedia } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.model';

describe('Component Tests', () => {

    describe('BidCategoryMedia Management Detail Component', () => {
        let comp: BidCategoryMediaDetailComponent;
        let fixture: ComponentFixture<BidCategoryMediaDetailComponent>;
        let service: BidCategoryMediaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [BidCategoryMediaDetailComponent],
                providers: [
                    BidCategoryMediaService
                ]
            })
            .overrideTemplate(BidCategoryMediaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BidCategoryMediaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BidCategoryMediaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new BidCategoryMedia(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bidCategoryMedia).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
