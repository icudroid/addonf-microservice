/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { BrandDetailComponent } from '../../../../../../main/webapp/app/entities/brand/brand-detail.component';
import { BrandService } from '../../../../../../main/webapp/app/entities/brand/brand.service';
import { Brand } from '../../../../../../main/webapp/app/entities/brand/brand.model';

describe('Component Tests', () => {

    describe('Brand Management Detail Component', () => {
        let comp: BrandDetailComponent;
        let fixture: ComponentFixture<BrandDetailComponent>;
        let service: BrandService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [BrandDetailComponent],
                providers: [
                    BrandService
                ]
            })
            .overrideTemplate(BrandDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BrandDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BrandService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Brand(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.brand).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
